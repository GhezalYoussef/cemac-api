package sncf.reseau.cemac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sncf.reseau.cemac.dto.AnalyseResultDto;
import sncf.reseau.cemac.dto.PeriodiciteDto;
import sncf.reseau.cemac.dto.RequeteDto;
import sncf.reseau.cemac.entity.Catenaire;
import sncf.reseau.cemac.entity.Requete;
import sncf.reseau.cemac.exception.ResourceNotFoundException;
import sncf.reseau.cemac.mapper.PeriodiciteDtoMapper;
import sncf.reseau.cemac.mapper.RequeteDtoMapper;
import sncf.reseau.cemac.repository.CatenaireRepository;
import sncf.reseau.cemac.repository.PeriodiciteRepository;
import sncf.reseau.cemac.repository.RequeteRepository;
import sncf.reseau.cemac.service.RequeteService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class RequeteServiceImpl implements RequeteService {

    private final RequeteRepository requeteRepository;

    private final RequeteDtoMapper requeteDtoMapper;

    private final CatenaireRepository catenaireRepository;

    private final PeriodiciteRepository periodiciteRepository;

    private final PeriodiciteDtoMapper periodiciteDtoMapper;

    @Autowired
    public RequeteServiceImpl(RequeteRepository requeteRepository,
                              RequeteDtoMapper requeteDtoMapper,
                              CatenaireRepository catenaireRepository,
                              PeriodiciteRepository periodiciteRepository,
                              PeriodiciteDtoMapper periodiciteDtoMapper){
        this.requeteDtoMapper = requeteDtoMapper;
        this.requeteRepository = requeteRepository;
        this.catenaireRepository = catenaireRepository;
        this.periodiciteRepository = periodiciteRepository;
        this.periodiciteDtoMapper = periodiciteDtoMapper;
    }

    @Override
    public List<RequeteDto> getAll() throws ResourceNotFoundException {
        log.info("Charger la liste des requêtes");
        return requeteRepository.findAll()
                .stream()
                .map(requeteDtoMapper::map)
                .toList();
    }

    @Override
    public RequeteDto getById(Long id) throws ResourceNotFoundException {
        log.info("Charger la requête avec l'id : [" + id + "]");
        return requeteRepository.findById(id)
                .map(requeteDtoMapper::map)
                .orElseThrow();
    }

    @Override
    public RequeteDto update(RequeteDto requeteDto) {
        log.info("Modifier la requête : [" + requeteDto.getRequeteRef() + "]");
        RequeteDto requeteUpdate;
        Requete requete = new Requete();
        requete.setId(requeteDto.getId());
        requete.setRequeteRef(requeteDto.getRequeteRef());
        requete.setDateCreation(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        requete.setCreateur(requeteDto.getCreateur());
        requete.setDateModification(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        requete.setModificateur(requeteDto.getModificateur());
        requeteUpdate = requeteDtoMapper.map(requeteRepository.saveAndFlush(requete));
        return requeteUpdate;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        log.info("Supprimer la requête : [" + id + "]");
        requeteRepository.deleteById(id);
    }

    @Override
    public RequeteDto getAnalyseResult(RequeteDto requeteDto) {
        List<AnalyseResultDto> analyseResultDtoList = new ArrayList<>();
        Catenaire catenaire = catenaireRepository.findById(requeteDto.getTypeInstallationTension()).orElseThrow();
        List<PeriodiciteDto> periodiciteDtoList = catenaire.getPeriodicites()
                .stream()
                .map(periodiciteDtoMapper::map)
                .toList();
        AtomicInteger i = new AtomicInteger();
        periodiciteDtoList.forEach(
                periodiciteDto -> {
                    AnalyseResultDto analyseResultDto = new AnalyseResultDto();
                    analyseResultDto.setRequete(requeteDto.getId());
                    analyseResultDto.setRefResult("OP_"+i.getAndIncrement());
                    analyseResultDto.setCategorie(requeteDto.getCategorieMaintenance().name());
                    analyseResultDto.setOperation(periodiciteDto.getLibelle());
                    analyseResultDto.setCategorie(periodiciteDto.getCategorieOperation());
                    analyseResultDto.setSousCategorie(periodiciteDto.getSousCategorieOperation());
                    analyseResultDtoList.add(analyseResultDto);
                });
        requeteDto.setAnalyseResultList(analyseResultDtoList);
        return requeteDto;
    }
}
