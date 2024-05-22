package sncf.reseau.cemac.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatenaireDto {

    private Long id;
    private Long familleCatenaire;
    private List<PeriodiciteDto> periodicites;
    private String libelle;

}
