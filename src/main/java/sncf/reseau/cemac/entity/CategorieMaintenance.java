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
@Table(name = "categorie_maintenance")
public class CategorieMaintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_ligne")
    private ELigne typeLigne;

    @Column(name = "panto_min")
    private Integer pantoMin;

    @Column(name = "panto_max")
    private Integer pantoMax;

    @Column(name = "vitesse_min")
    private Integer vitesseMin;

    @Column(name = "vitesse_max")
    private Integer vitesseMax;

    @Column(name = "categorie_maintenance")
    private String categorieMaintenance;

}
