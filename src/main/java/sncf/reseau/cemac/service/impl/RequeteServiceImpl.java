package sncf.reseau.cemac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sncf.reseau.cemac.dto.AnalyseResultDto;
import sncf.reseau.cemac.dto.PeriodiciteDto;
import sncf.reseau.cemac.dto.RequeteDto;
import sncf.reseau.cemac.entity.AnalyseResult;
import sncf.reseau.cemac.entity.Catenaire;
import sncf.reseau.cemac.entity.Periodicite;
import sncf.reseau.cemac.entity.Requete;
import sncf.reseau.cemac.enumeration.EUnit;
import sncf.reseau.cemac.exception.ResourceNotFoundException;
import sncf.reseau.cemac.mapper.PeriodiciteDtoMapper;
import sncf.reseau.cemac.mapper.RequeteDtoMapper;
import sncf.reseau.cemac.repository.AnalyseResultRepository;
import sncf.reseau.cemac.repository.CatenaireRepository;
import sncf.reseau.cemac.repository.PeriodiciteRepository;
import sncf.reseau.cemac.repository.RequeteRepository;
import sncf.reseau.cemac.service.RequeteService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RequeteServiceImpl implements RequeteService {

    private final RequeteRepository requeteRepository;

    private final RequeteDtoMapper requeteDtoMapper;

    private final CatenaireRepository catenaireRepository;

    private final PeriodiciteRepository periodiciteRepository;

    private final PeriodiciteDtoMapper periodiciteDtoMapper;

    private final AnalyseResultRepository analyseResultRepository;

    @Autowired
    public RequeteServiceImpl(RequeteRepository requeteRepository,
                              RequeteDtoMapper requeteDtoMapper,
                              CatenaireRepository catenaireRepository,
                              PeriodiciteRepository periodiciteRepository,
                              PeriodiciteDtoMapper periodiciteDtoMapper,
                              AnalyseResultRepository analyseResultRepository){
        this.requeteDtoMapper = requeteDtoMapper;
        this.requeteRepository = requeteRepository;
        this.catenaireRepository = catenaireRepository;
        this.periodiciteRepository = periodiciteRepository;
        this.periodiciteDtoMapper = periodiciteDtoMapper;
        this.analyseResultRepository = analyseResultRepository;
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
        Catenaire catenaire = catenaireRepository.findById(requeteDto.getTypeInstallationTension()).orElseThrow();
        Requete requete = new Requete();
        requete.setId(requeteDto.getId());
        requete.setRequeteRef(requeteDto.getRequeteRef());
        requete.setDateCreation(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        requete.setCreateur(requeteDto.getCreateur());
        requete.setDateModification(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        requete.setModificateur(requeteDto.getModificateur());
        requete.setTypeLigne(requeteDto.getTypeLigne());
        requete.setNbrPanto(requeteDto.getNbrPanto());
        requete.setVitesse(requeteDto.getVitesse());
        requete.setCategorieMaintenance(requeteDto.getCategorieMaintenance());
        requete.setTypeInstallationTension(catenaire);
        requete.setNombreML(requeteDto.getNombreML());
        requete.setNombreIS(requeteDto.getNombreIS());
        requete.setNombreAIG(requeteDto.getNombreAIG());
        requete.setNombreAT(requeteDto.getNombreAT());
        requete.setNombreIA(requeteDto.getNombreIA());
        requete.setCategorieMaintenance(requeteDto.getCategorieMaintenance());
        requete.setAnalyseResultList(requeteDto.getAnalyseResultList()
                .stream()
                .map(analyseResultDto -> saveAnalyseResult(analyseResultDto, requete))
                .collect(Collectors.toList()));
        return requeteDtoMapper.map(requeteRepository.saveAndFlush(requete));
    }

    public AnalyseResult saveAnalyseResult(AnalyseResultDto analyseResultDto, Requete requete){
        log.info("Sauvegarder l'analyse : [" + analyseResultDto.toString() + "]");
        AnalyseResult analyseResult = new AnalyseResult();
        analyseResult.setRequete(requete);
        analyseResult.setId(analyseResultDto.getId());
        analyseResult.setCategorie(analyseResultDto.getRefResult());
        analyseResult.setSousCategorie(analyseResultDto.getSousCategorie());
        analyseResult.setOperation(analyseResultDto.getOperation());
        analyseResult.setCategorieMaintenance(analyseResultDto.getCategorieMaintenance());
        analyseResult.setUop(analyseResultDto.getUop());
        analyseResult.setCout(analyseResultDto.getCout());
        return analyseResultRepository.save(analyseResult);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        log.info("Supprimer la requête : [" + id + "]");
        requeteRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RequeteDto getAnalyseResult(RequeteDto requeteDto) {

        List<AnalyseResultDto> analyseResultDtoList = new ArrayList<>();
        Set<String> libelleList = new LinkedHashSet<>();

        List<PeriodiciteDto> periodiciteDtoListByCategorie = getCatenaireById(requeteDto.getTypeInstallationTension())
                .getPeriodicites()
                .stream()
                .filter(periodicite -> periodicite.getCategorieMaintenance().equals(requeteDto.getCategorieMaintenance()))
                .map(periodiciteDtoMapper::map)
                .toList();

        List<PeriodiciteDto> periodiciteDtoAllList = periodiciteRepository
                .findAll()
                .stream()
                .filter(periodicite -> libelleList.add(periodicite.getLibelle() + "-" + periodicite.getSousOperation()))
                .map(periodiciteDtoMapper::map)
                .toList();

        AtomicInteger i = new AtomicInteger();
        periodiciteDtoAllList.forEach(
                periodiciteDto -> {
                    AnalyseResultDto analyseResultDto = new AnalyseResultDto();
                    analyseResultDto.setRequete(requeteDto.getId());
                    analyseResultDto.setRefResult("OP_" + i.getAndIncrement());
                    analyseResultDto.setCategorie(periodiciteDto.getCategorieOperation());
                    analyseResultDto.setSousCategorie(periodiciteDto.getSousCategorieOperation());
                    analyseResultDto.setOperation(periodiciteDto.getLibelle());
                    analyseResultDto.setSousOperation(periodiciteDto.getSousOperation());
                    analyseResultDto.setCategorieMaintenance(periodiciteDto.getCategorieMaintenance().name());
                    if(periodiciteDtoListByCategorie.contains(periodiciteDto)){
                        analyseResultDto.setUop(getUOP(requeteDto, periodiciteDto));
                    }else{
                        analyseResultDto.setUop(0f);
                    }

                    analyseResultDto.setCout(0f);
                    analyseResultDtoList.add(analyseResultDto);
                });

        requeteDto.setAnalyseResultList(analyseResultDtoList);
        return requeteDto;
    }

    public Float getUOP(RequeteDto requeteDto, PeriodiciteDto periodiciteDto) {
        float result = 0;

        switch (periodiciteDto.getSousOperation()) {
            case "AT":
                result = calculateUOP(requeteDto.getNombreAT(), periodiciteDto);
                break;
            case "IS":
                result = calculateUOP(requeteDto.getNombreIS(), periodiciteDto);
                break;
            case "IA":
                result = calculateUOP(requeteDto.getNombreIA(), periodiciteDto);
                break;
            case "AIG":
                result = calculateUOP(requeteDto.getNombreAIG(), periodiciteDto);
                break;
            default:
                result = calculateUOP(requeteDto.getNombreML(), periodiciteDto);
                break;
        }

        return result;
    }

    private Float calculateUOP(int nombre, PeriodiciteDto periodiciteDto) {
        if (nombre <= 0) {
            return 0f;
        }

        switch (periodiciteDto.getUnit()) {
            case ANS:
                return nombre / (float) periodiciteDto.getPeriode();
            case JOURS:
                return nombre / ((float) periodiciteDto.getPeriode() / 365);
            default:
                return nombre / ((float) periodiciteDto.getPeriode() / 12);
        }
    }

    @Transactional
    public Catenaire getCatenaireById(Long id) {
        Catenaire catenaire = catenaireRepository.findById(id).orElseThrow();
        catenaire.getPeriodicites().size(); // Initialize the collection
        return catenaire;
    }
}
