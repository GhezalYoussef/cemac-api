package sncf.reseau.cemac.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "id_Sncf", unique = true)
    private String idSncf;

    private String nom;

    private String prenom;

    @ManyToOne
    @JoinColumn(name = "profil")
    private Profil profil;

}
