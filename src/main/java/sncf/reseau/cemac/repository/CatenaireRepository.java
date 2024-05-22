package sncf.reseau.cemac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sncf.reseau.cemac.entity.Catenaire;
@Repository
public interface CatenaireRepository extends JpaRepository<Catenaire, Long> {
}
