package sncf.reseau.cemac.mapper;

import org.springframework.stereotype.Component;
import sncf.reseau.cemac.dto.CatenaireDto;
import sncf.reseau.cemac.entity.Catenaire;

import java.util.ArrayList;
import java.util.List;

@Component
public class CatenaireDtoMapper {


    public CatenaireDto map(Catenaire catenaire){

        return CatenaireDto.builder()
                .id(catenaire.getId())
                .familleCatenaire(catenaire.getFamilleCatenaire().getId())
                .libelle(catenaire.getLibelle())
                .build();
    }

    public List<CatenaireDto> map(List<Catenaire> catenaireList){
        List<CatenaireDto> results = new ArrayList<>();
        catenaireList.forEach(
                catenaire -> results.add(map(catenaire))
        );
        return results;
    }

}
