package sncf.reseau.cemac.mapper;

import org.springframework.stereotype.Component;
import sncf.reseau.cemac.dto.CategorieMaintenanceDto;
import sncf.reseau.cemac.entity.CategorieMaintenance;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategorieMaintenanceDtoMapper {

    public CategorieMaintenanceDto map(CategorieMaintenance categorieMaintenance){
        return CategorieMaintenanceDto.builder()
                .id(categorieMaintenance.getId())
                .typeLigne(categorieMaintenance.getTypeLigne())
                .pantoMin(categorieMaintenance.getPantoMin())
                .pantoMax(categorieMaintenance.getPantoMax())
                .vitesseMax(categorieMaintenance.getVitesseMax())
                .vitesseMin(categorieMaintenance.getVitesseMin())
                .categorieMaintenance(categorieMaintenance.getCategorieMaintenance())
                .build();
    }

    public List<CategorieMaintenanceDto> map(List<CategorieMaintenance> categorieMaintenanceList){
        List<CategorieMaintenanceDto> results = new ArrayList<>();
        categorieMaintenanceList.forEach(
                categorieMaintenance -> results.add(map(categorieMaintenance))
        );

        return results;
    }
}
