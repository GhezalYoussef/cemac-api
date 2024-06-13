package sncf.reseau.cemac.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import sncf.reseau.cemac.enumeration.ELigne;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "requete")
public class Requete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "requete_ref")
    private String requeteRef;

    @Column(name = "date_creation")
    private Instant dateCreation;

    private String createur;

    @Column(name = "date_modification")
    private Instant dateModification;

    private String modificateur;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_ligne")
    private ELigne typeLigne;

    @Column(name = "nbr_panto")
    private Integer nbrPanto;

    @Column(name = "vitesse")
    private Integer vitesse;

    @Column(name = "categorie_maintenance")
    private String categorieMaintenance;

    @Column(name = "nombre_ml")
    private Integer nombreML;

    @Column(name = "nombre_is")
    private Integer nombreIS;

    @Column(name = "nombre_aig")
    private Integer nombreAIG;

    @Column(name = "nombre_at")
    private Integer nombreAT;

    @Column(name = "nombre_ia")
    private Integer nombreIA;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "type_installation_tension")
    private Catenaire typeInstallationTension;

    @JoinColumn(name="requete")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnalyseResult> analyseResultList;

}
