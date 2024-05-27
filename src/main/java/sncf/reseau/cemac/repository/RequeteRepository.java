package sncf.reseau.cemac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sncf.reseau.cemac.entity.Requete;

@Repository
public interface RequeteRepository extends JpaRepository<Requete, Long> {
}
