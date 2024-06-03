package sncf.reseau.cemac.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sncf.reseau.cemac.enumeration.EProfil;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfilDto {

    private Long id;
    private EProfil libelle;
}
