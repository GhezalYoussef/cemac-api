package sncf.reseau.cemac.entity;

import jakarta.persistence.*;
import lombok.*;
import sncf.reseau.cemac.enumeration.ECategorie;
import sncf.reseau.cemac.enumeration.ELigne;
import sncf.reseau.cemac.enumeration.ETension;
import sncf.reseau.cemac.enumeration.EUnit;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "periodicite")
public class Periodicite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "periodicite_catenaire",
            joinColumns = @JoinColumn(name = "periodicite_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "catenaire_id", referencedColumnName="id")
    )
    private List<Catenaire> catenaires;

    @Column(name = "categorie_operation")
    private String categorieOperation;

    @Column(name = "sous_categorie_operation")
    private String sousCategorieOperation;

    private String libelle;

    @Column(name = "sous_operation")
    private String sousOperation;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_ligne")
    private ELigne typeLigne;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tension")
    private ETension tension;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "categorie_maintenance")
    private ECategorie categorieMaintenance;

    @NonNull
    @Enumerated(EnumType.STRING)
    private EUnit unit;

    private Integer periode;

}
