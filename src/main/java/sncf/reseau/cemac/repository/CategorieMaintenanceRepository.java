package sncf.reseau.cemac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sncf.reseau.cemac.entity.CategorieMaintenance;

@Repository
public interface CategorieMaintenanceRepository extends JpaRepository<CategorieMaintenance, Long> {
}
