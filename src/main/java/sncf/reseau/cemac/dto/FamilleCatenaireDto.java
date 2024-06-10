package sncf.reseau.cemac.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sncf.reseau.cemac.enumeration.ELigne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilleCatenaireDto {

    private Long id;
    private ELigne typeLigne;
    private String libelle;

}
