package sncf.reseau.cemac.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sncf.reseau.cemac.dto.RequeteDto;
import sncf.reseau.cemac.entity.Requete;

@Component
public class RequeteDtoMapper {

    @Autowired
    AnalyseResultDtoMapper analyseResultDtoMapper;
    public RequeteDto map(Requete requete){
        return RequeteDto.builder()
                .id(requete.getId())
                .requeteRef(requete.getRequeteRef())
                .dateCreation(requete.getDateCreation().toString())
                .createur(requete.getCreateur())
                .dateModification(requete.getDateModification().toString())
                .modificateur(requete.getModificateur())
                .typeLigne(requete.getTypeLigne())
                .nbrPanto(requete.getNbrPanto())
                .vitesse(requete.getVitesse())
                .categorieMaintenance(requete.getCategorieMaintenance())
                .typeInstallationTension(requete.getTypeInstallationTension().getId())
                .nombreML(requete.getNombreML())
                .nombreIS(requete.getNombreIS())
                .nombreAIG(requete.getNombreAIG())
                .nombreAT(requete.getNombreAT())
                .nombreIA(requete.getNombreIA())
                .analyseResultList(analyseResultDtoMapper.map(requete.getAnalyseResultList()))
                .build();
    }

}
