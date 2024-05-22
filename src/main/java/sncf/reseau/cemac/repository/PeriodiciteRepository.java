package sncf.reseau.cemac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sncf.reseau.cemac.entity.Periodicite;
@Repository
public interface PeriodiciteRepository extends JpaRepository<Periodicite, Long> {
}
