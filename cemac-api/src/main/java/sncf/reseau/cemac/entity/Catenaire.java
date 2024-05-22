package sncf.reseau.cemac.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "catenaire")
public class Catenaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "famille_catenaire")
    private FamilleCatenaire familleCatenaire;

    @ManyToMany(mappedBy = "catenaires")
    private Set<Periodicite> periodicites =  new HashSet<>();

    private String libelle;

}
