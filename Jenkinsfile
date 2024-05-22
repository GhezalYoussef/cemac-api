#!/usr/bin/env groovy

// Version du template Maven : 2.0.6
// Documentation : https://docs.apps.eul.sncf.fr/construire/templates/maven/

// Déclaration de la shared library build_libs
@Library('build_libs') _

// Options du build
Map options = [
    // Nombre de builds à conserver dans l'historique des builds du job Jenkins
    NUM_TO_KEEP: 10,
    // Branche sur laquelle l'analyse qualité et la publication sont applicables
    BRANCH_DEVELOP: 'develop',
    // Version de l'image Maven utilisée pour le build
    MAVEN_VERSION: '3.9.6-eclipse-temurin-21-alpine',
    // Définir à true si une image Docker applicative est à produire
    DOCKER_BUILD: false,
    // Définir à true pour des logs du build Docker plus détaillés
    IMAGE_BUILD_VERBOSE: false,
    // Chemin du pom maven local au workspace
    POM_PATH: 'pom.xml',
]

// Valorisation des options par défaut
options = defaultBuildOptions(options)

// Propriétés du job
properties([
    // Connexion GitLab
    gitLabConnection("${options['GITLAB_NAME']}"),
    // Conservation des N dernières exécutions
    buildDiscarder(logRotator(numToKeepStr: "${options['NUM_TO_KEEP']}")),
    // Déclenchement du job via webhook lors de push de nouveau code ou de création de merge request
    pipelineTriggers([[$class: 'GitLabPushTrigger', triggerOnPush: true, triggerOnMergeRequest: true, branchFilterType: 'All']]),
])

// L'analyse qualité, la publication des artefacts et le build Docker sont lancés uniquement sur la branche de développement
Boolean qualityAnalysisEnabled = env.BRANCH_NAME == options['BRANCH_DEVELOP']
Boolean publishEnabled = env.BRANCH_NAME == options['BRANCH_DEVELOP']
Boolean dockerEnabled = env.BRANCH_NAME == options['BRANCH_DEVELOP'] && options['DOCKER_BUILD'] == true

// Pour extraction d'informations à partir du pom du projet
def pom

// Sinon, s'il est activé, l'horodatage des logs du CLI eul est décalé d'une heure
env.TZ = 'Europe/Paris'

// Horodatage des lignes de log Jenkins
timestamps {
    // Prise en charge des codes ANSI pour affichage de logs Jenkins colorisés
    ansiColor('xterm') {
        withTools([
            [name: 'maven', version: "${options['MAVEN_VERSION']}"],
            dockerEnabled ? [name: 'buildkit', image: 'moby/buildkit', version: 'rootless'] : [:],
        ]) {
            try {
                stage('Checkout') {
                    println '🔰 Récupération du code source'
                    scmInfo = checkout scm
                    env.GIT_URL = scmInfo.GIT_URL
                    env.GIT_COMMIT = scmInfo.GIT_COMMIT
                    println '✔️ Récupération du code source effectuée'
                }
                stage('Setup') {
                    println '🔰 Configuration du build'
                    // Extraction de la version à partir du pom
                    pom = readMavenPom file: options['POM_PATH']
                    options['VERSION'] = pom.version
                    println '✔️️ Configuration du build effectuée'
                }
                stage('Build') {
                    container('maven') {
                        println '🔰 Compilation'
                        sh 'eul mvn compile -Dmaven.test.skip'
                        println '✔️ Build effectué'
                    }
                }
                stage('Tests') {
                    container('maven') {
                        println '🔰 Exécution des tests unitaires'
                        if (sh(script: 'eul mvn org.jacoco:jacoco-maven-plugin:prepare-agent test org.jacoco:jacoco-maven-plugin:report', returnStatus: true)) {
                            unstable '⚠️️ Les tests unitaires ont échoué'
                        }
                        println '✔️ Exécution des tests unitaires effectuée'
                    }
                }
                stage('QA') {
                    container('maven') {
                        when(qualityAnalysisEnabled) {
                            println '🔰 Analyse qualité'
                            String sonarBranch = env.BRANCH_NAME?.replaceAll(/[\\]/, '_').replaceAll(/[, ]/, '')
                            String sonarProjectName = "${pom.name} ${sonarBranch}".trim()
                            withSonarQubeEnv('sonarqube') {
                                sh """\
                                    eul mvn sonar:sonar \
                                        -DskipTests=true \
                                        -Dsonar.projectKey=${options['PROJECT_SONAR_KEY_BRANCH']} \
                                        -Dsonar.projectName="${sonarProjectName}" \
                                        -Dsonar.links.ci=${JOB_URL} \
                                        -Dsonar.links.homepage=${GIT_URL}
                                """
                            }
                            println '✔️ Analyse qualité effectuée'
                        }
                    }
                }
                stage('Local CVE scan') {
                    container('maven') {
                        println '🔰 Scan de sécurité local sur les artefacts produits'
                        // Scan CVE déclenché au plus tôt pour ne pas publier d'artefact avec vulnérabilités critiques
                        if (sh(script: 'eul artefacts cve-scan --local --critical --mvn', returnStatus: true)) {
                            // La présence de CVE critiques dans les artefacts est bloquante
                            error "❌ Le scan de CVE a identifié des vulnérabilités critiques dans l'artefact ou ses dépendances"
                        }
                        println '✔️️ Scan CVE local effectué'
                    }
                }
                stage('Publish') {
                    when(publishEnabled) {
                        container('maven') {
                            println "🔰 Publication des artefacts du projet dans Artifactory depuis la branche ${BRANCH_NAME}"
                            // Publication des artefacts dans Artifactory
                            sh 'eul mvn install -DskipTests=true'
                            // Publication des informations de build dans Artifactory
                            sh 'eul artefacts build-publish'
                            // Publication Jenkins du lien sur le build Artifactory
                            publishArtifactoryBuildLink()
                            println '✔️ Publication des artefacts effectuée'
                        }
                    }
                }
                stage('CVE scan') {
                    when(publishEnabled) {
                        container('maven') {
                            println '🔰 Scan CVE distant sur les artefacts produits sur Artifactory'
                            // Pour des raisons de performances, l'éxecution du scan de build est asynchrone. C'est à dire, le résultat final du scan n'est pas attendu par le pipeline.
                            // Le lien artifactory vers le scan de build est publié directement dans le job et accessible.
                            // Pour forcer le pipeline à attendre le résultat du scan de build, il faut rajouter le flag `--wait-for-result` à la commande de scan cve comme suit:
                            // <eul artefacts cve-scan --wait-for-result>
                            // cf: https://docs.apps.eul.sncf.fr/share/securise-scan-dependances
                            if (sh(script: 'eul artefacts cve-scan', returnStatus: true)) {
                                // Si le pipeline arrive à ce stage cela veut dire qu'il a passé le scan local et ne présente pas de CVE critiques mais hautes.
                                // Pour mettre la pipeline en erreur, remplacer unstable "⚠️..." par error "❌..." dans la ligne suivante
                                unstable "⚠️️ Le scan de CVE a identifié des vulnérabilités hautes dans l'artefact ou ses dépendances"
                            }
                            println '✔️ Scan CVE distant effectué'
                        }
                    }
                }
                stage('Docker build') {
                    when(dockerEnabled) {
                        container('buildkit') {
                            println '🔰 build Docker avec Buildkit'
                            String dockerVerboseOption = options['IMAGE_BUILD_VERBOSE'] ? '--debug' : ''
                            env.IMAGE_NAME = options['PROJECT_NAME']
                            env.IMAGE_VERSION = options['VERSION']
                            result = sh(script: "eul image $dockerVerboseOption build docker --tag=$IMAGE_NAME:$IMAGE_VERSION --build-arg 'VERSION=$IMAGE_VERSION'", returnStatus: true)
                            if (result != 0) {
                                error '❌ Le build Docker avec BuildKit a échoué'
                            }
                            println '✔️ build Docker effectué'
                        }
                    }
                }

                stage('Docker CVE scan') {
                    when(dockerEnabled) {
                        container('buildkit') {
                            println '🔰 Scan CVE distant sur l\'image docker produite'
                            // L'éxecution du scan docker est synchrone. C'est à dire, le résultat final du scan est attendu par le pipeline.
                            // Pour forcer le pipeline à ne pas attendre le résultat du scan docker, il faut enlever le flag `--wait-for-result` à la commande de scan comme suit:
                            // <eul image scan>
                            // cf: https://docs.apps.eul.sncf.fr/share/scan-conteneur
                            result = sh(script: "eul image scan $IMAGE_NAME:$IMAGE_VERSION --wait-for-result", returnStatus: true)
                            if (result > 0) {
                                if (result == 4) {
                                    // La présence de CVE critiques dans l'image docker n'est pas bloquante.
                                    // Pour mettre le pipeline en erreur, remplacer unstable "⚠️..." par error "❌..." dans la ligne suivante.
                                    unstable "⚠️️ Le scan CVE distant Docker a identifié des vulnérabilités critiques dans l'image produite"
                                } else {
                                    error '❌ Le Scan CVE distant Docker a échoué'
                                }
                            }

                            println '✔️ Scan CVE distant sur l\'image docker produite effectué'
                        }
                    }
                }

                println '👍 Build du job de snapshot terminé avec succès'
                updateGitlabCommitStatus name: 'build', state: 'success'
                currentBuild.result = 'SUCCESS'
            } catch (all) {
                updateGitlabCommitStatus name: 'build', state: 'failed'
                currentBuild.result = 'FAILURE'
                // Envoi d'un mail en cas d'échec
                emailext(
                    body: '$DEFAULT_CONTENT',
                    subject: '$DEFAULT_SUBJECT',
                    // Destinataires : auteurs d'une modification du code et déclencheur du pipeline
                    recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                    // Pour également envoyer le mail à une liste d'adresses (séparées par des ,) :
                    //to: "adressmail1, adressmail2"
                )
                throw all
            } finally {
                // Collecte et remontée dans Jenkins des problèmes rencontrés pendant le build, ainsi que les résultats des tests unitaires.
                // Documentation : https://docs.apps.eul.sncf.fr/share/sharedlib-addngissuesreporttojob/
                addNgIssuesReportToJob(
                    'tools': [
                        'cveScan',
                        'maven',
                        'taskScanner',
                        ['junitPlugin': ['testResults': '**/target/surefire-reports/*.xml']],
                        ['junitParser': ['pattern': '**/target/surefire-reports/*.xml']],
                    ]
                )
                // Pour le suivi et les indicateurs eUL
                eulNotify()
            }
        }
    }
}
