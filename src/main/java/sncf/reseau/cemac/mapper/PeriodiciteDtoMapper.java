package sncf.reseau.cemac.mapper;

import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sncf.reseau.cemac.dto.PeriodiciteDto;
import sncf.reseau.cemac.entity.Periodicite;

import java.util.ArrayList;
import java.util.List;

@Component
public class PeriodiciteDtoMapper {

    @Autowired
    CatenaireDtoMapper catenaireDtoMapper;

    public PeriodiciteDto map(Periodicite periodicite){
        return PeriodiciteDto.builder()
                .id(periodicite.getId())
                .catenaires(catenaireDtoMapper.map(periodicite.getCatenaires()))
                .categorieOperation(periodicite.getCategorieOperation())
                .sousCategorieOperation(periodicite.getSousCategorieOperation())
                .libelle(periodicite.getLibelle())
                .sousOperation(periodicite.getSousOperation())
                .typeLigne(periodicite.getTypeLigne())
                .tension(periodicite.getTension())
                .categorieMaintenance(periodicite.getCategorieMaintenance())
                .unit(periodicite.getUnit())
                .periode(periodicite.getPeriode())
                .build();
    }

    public List<PeriodiciteDto> map(List<Periodicite> periodiciteList){
        List<PeriodiciteDto> results = new ArrayList<>();
        periodiciteList.forEach(
                periodicite -> map(periodicite)
        );
        return results;
    }
}
