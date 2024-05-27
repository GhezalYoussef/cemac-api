package sncf.reseau.cemac.dto;

import lombok.*;
import sncf.reseau.cemac.enumeration.ECategorie;
import sncf.reseau.cemac.enumeration.ELigne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequeteDto {

    private Long id;
    private String requeteRef;
    private String dateCreation;
    private String createur;
    private String dateModification;
    private String modificateur;
    private ELigne typeLigne;
    private Integer nbrPanto;
    private Integer vitesse;
    private ECategorie categorieMaintenance;
    private Long typeInstallationTension;
    private Integer nombreML;
    private Integer nombreIS;
    private Integer nombreAIG;
    private Integer nombreAT;
    private Integer nombreIA;
}
