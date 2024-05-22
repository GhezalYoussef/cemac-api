package sncf.reseau.cemac.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sncf.reseau.cemac.enumeration.ECategorie;
import sncf.reseau.cemac.enumeration.ELigne;
import sncf.reseau.cemac.enumeration.ETension;
import sncf.reseau.cemac.enumeration.EUnit;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeriodiciteDto {

    private Long id;
    private List<CatenaireDto> catenaires;
    private String categorieOperation;
    private String sousCategorieOperation;
    private String libelle;
    private String sousOperation;
    private ELigne typeLigne;
    private ETension tension;
    private ECategorie categorieMaintenance;
    private EUnit unit;
    private Integer periode;

}
