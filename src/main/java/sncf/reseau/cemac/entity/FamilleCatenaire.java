package sncf.reseau.cemac.entity;

import jakarta.persistence.*;
import lombok.*;
import sncf.reseau.cemac.enumeration.ELigne;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "famille_catenaire")
public class FamilleCatenaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Enumerated(EnumType.STRING)
    private ELigne typeLigne;

    private String libelle;

}
