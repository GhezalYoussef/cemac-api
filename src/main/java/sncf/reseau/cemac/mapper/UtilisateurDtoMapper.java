package sncf.reseau.cemac.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sncf.reseau.cemac.dto.UtilisateurDto;
import sncf.reseau.cemac.entity.Utilisateur;

import java.util.ArrayList;
import java.util.List;

@Component
public class UtilisateurDtoMapper {

    @Autowired
    ProfilDtoMapper profilDtoMapper;

    public UtilisateurDto map(Utilisateur utilisateur){
        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .idSncf(utilisateur.getIdSncf())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .profil(profilDtoMapper.map(utilisateur.getProfil()))
                .build();
    }

    public List<UtilisateurDto> map(List<Utilisateur> utilisateurList){
        List<UtilisateurDto> results = new ArrayList<>();
        utilisateurList.forEach(
                utilisateur -> results.add(map(utilisateur))
        );
        return results;
    }

}
