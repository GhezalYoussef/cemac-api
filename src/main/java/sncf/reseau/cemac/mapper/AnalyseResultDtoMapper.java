package sncf.reseau.cemac.mapper;

import org.springframework.stereotype.Component;
import sncf.reseau.cemac.dto.AnalyseResultDto;
import sncf.reseau.cemac.entity.AnalyseResult;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnalyseResultDtoMapper {


    public AnalyseResultDto map(AnalyseResult analyseResult){
        return AnalyseResultDto.builder()
                .id(analyseResult.getId())
                .requete(analyseResult.getRequete().getId())
                .refResult(analyseResult.getRefResult())
                .refResult(analyseResult.getRefResult())
                .sousCategorie(analyseResult.getSousCategorie())
                .categorie(analyseResult.getCategorie())
                .nbr(analyseResult.getNbr())
                .cout(analyseResult.getCout())
                .build();
    }

    public List<AnalyseResultDto> map(List<AnalyseResult> analyseResultList){

        List<AnalyseResultDto> results = new ArrayList<>();
        analyseResultList.forEach(
                analyseResult -> results.add(map(analyseResult))
        );
        return results;
    }

}
