package sncf.reseau.cemac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sncf.reseau.cemac.entity.Profil;


@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {
}
