package sncf.reseau.cemac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sncf.reseau.cemac.entity.AnalyseResult;

@Repository
public interface AnalyseResultRepository extends JpaRepository<AnalyseResult, Long> {

}
