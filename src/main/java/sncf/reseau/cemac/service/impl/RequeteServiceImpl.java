package sncf.reseau.cemac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sncf.reseau.cemac.dto.RequeteDto;
import sncf.reseau.cemac.entity.Requete;
import sncf.reseau.cemac.exception.ResourceNotFoundException;
import sncf.reseau.cemac.mapper.RequeteDtoMapper;
import sncf.reseau.cemac.repository.RequeteRepository;
import sncf.reseau.cemac.service.RequeteService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
public class RequeteServiceImpl implements RequeteService {

    private final RequeteRepository requeteRepository;

    private final RequeteDtoMapper requeteDtoMapper;

    @Autowired
    public RequeteServiceImpl(RequeteRepository requeteRepository,
                              RequeteDtoMapper requeteDtoMapper){
        this.requeteDtoMapper = requeteDtoMapper;
        this.requeteRepository = requeteRepository;
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
        return null;
    }
}
