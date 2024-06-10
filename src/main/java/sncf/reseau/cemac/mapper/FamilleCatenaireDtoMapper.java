package sncf.reseau.cemac.mapper;

import org.springframework.stereotype.Component;
import sncf.reseau.cemac.dto.FamilleCatenaireDto;
import sncf.reseau.cemac.entity.FamilleCatenaire;

import java.util.ArrayList;
import java.util.List;

@Component
public class FamilleCatenaireDtoMapper {

    public FamilleCatenaireDto map(FamilleCatenaire familleCatenaire){

        return FamilleCatenaireDto.builder()
                .id(familleCatenaire.getId())
                .typeLigne(familleCatenaire.getTypeLigne())
                .libelle(familleCatenaire.getLibelle())
                .build();
    }

    public List<FamilleCatenaireDto> map(List<FamilleCatenaire> familleCatenaireList){
        List<FamilleCatenaireDto> results = new ArrayList<>();
        familleCatenaireList.forEach(
                familleCatenaire -> map(familleCatenaire)
        );
        return results;
    }
}
