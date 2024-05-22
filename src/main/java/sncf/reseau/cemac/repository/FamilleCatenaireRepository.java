package sncf.reseau.cemac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sncf.reseau.cemac.entity.FamilleCatenaire;
@Repository
public interface FamilleCatenaireRepository extends JpaRepository<FamilleCatenaire, Long> {
}
