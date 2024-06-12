package sncf.reseau.cemac.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDto {

    private Long id;
    private String idSncf;
    private String nom;
    private String prenom;
    private ProfilDto profil;
}
