package sncf.reseau.cemac.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "analyse_result")
public class AnalyseResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "requete")
    private Requete requete;

    @Column(name = "ref_result")
    private String refResult;

    private String categorie;

    @Column(name = "sous_categorie")
    private String sousCategorie;

    private String operation;

    @Column(name = "categorie_maintenance")
    private String categorieMaintenance;

    private Float uop;

    private Float cout;
}
