package sncf.reseau.cemac.entity;

import jakarta.persistence.*;
import lombok.*;
import sncf.reseau.cemac.enumeration.EProfil;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profil")
public class Profil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "libelle" ,unique = true)
    private EProfil libelle;

}
