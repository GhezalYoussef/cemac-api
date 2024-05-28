package sncf.reseau.cemac.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyseResultDto {

    private Long id;
    private Long requete;
    private String refResult;
    private String categorie;
    private String sousCategorie;
    private String operation;
    private String categorieMaintenance;
    private Float nbr;
    private Float cout;

}
