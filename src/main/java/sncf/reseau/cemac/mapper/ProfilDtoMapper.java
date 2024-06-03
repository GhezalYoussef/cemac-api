package sncf.reseau.cemac.mapper;

import org.springframework.stereotype.Component;
import sncf.reseau.cemac.dto.ProfilDto;
import sncf.reseau.cemac.entity.Profil;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfilDtoMapper {

    public ProfilDto map(Profil profil){
        return ProfilDto.builder()
                .id(profil.getId())
                .libelle(profil.getLibelle())
                .build();
    }

    public List<ProfilDto> map(List<Profil> profilList){
        List<ProfilDto> results = new ArrayList<>();
        profilList.forEach(
                profil -> results.add(map(profil))
        );
        return results;
    }

}
