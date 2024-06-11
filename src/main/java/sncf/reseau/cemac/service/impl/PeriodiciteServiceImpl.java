package sncf.reseau.cemac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sncf.reseau.cemac.dto.CatenaireDto;
import sncf.reseau.cemac.dto.PeriodiciteDto;
import sncf.reseau.cemac.entity.Catenaire;
import sncf.reseau.cemac.entity.Periodicite;
import sncf.reseau.cemac.exception.ResourceNotFoundException;
import sncf.reseau.cemac.mapper.CatenaireDtoMapper;
import sncf.reseau.cemac.mapper.PeriodiciteDtoMapper;
import sncf.reseau.cemac.repository.CatenaireRepository;
import sncf.reseau.cemac.repository.PeriodiciteRepository;
import sncf.reseau.cemac.service.PeriodiciteService;

import java.util.List;

@Slf4j
@Service
public class PeriodiciteServiceImpl implements PeriodiciteService {

    private final PeriodiciteRepository periodiciteRepository;

    private final CatenaireRepository catenaireRepository;

    private final CatenaireDtoMapper catenaireDtoMapper;

    private final PeriodiciteDtoMapper periodiciteDtoMapper;

    @Autowired
    public PeriodiciteServiceImpl(PeriodiciteRepository periodiciteRepository,
                                  PeriodiciteDtoMapper periodiciteDtoMapper,
                                  CatenaireDtoMapper catenaireDtoMapper,
                                  CatenaireRepository catenaireRepository) {
        this.periodiciteRepository = periodiciteRepository;
        this.periodiciteDtoMapper = periodiciteDtoMapper;
        this.catenaireRepository = catenaireRepository;
        this.catenaireDtoMapper = catenaireDtoMapper;
    }

    @Override
    public List<PeriodiciteDto> getPeriodiciteList() throws ResourceNotFoundException {
        log.info("Charger la liste des periodicites");
        return periodiciteRepository.findAll()
                .stream()
                .map(periodiciteDtoMapper::map)
                .toList();
    }

    @Override
    public PeriodiciteDto update(PeriodiciteDto periodiciteDto) throws ResourceNotFoundException {

        log.info("Modifier la periodicite : [" + periodiciteDto.toString() + "]");

        Periodicite periodicite = new Periodicite();
        List<Catenaire> catenaireList = catenaireRepository.findAllById(
                periodiciteDto.getCatenaires()
                        .stream()
                        .map(CatenaireDto::getId)
                        .toList()
        );

        periodicite.setId(periodiciteDto.getId());
        periodicite.setCatenaires(catenaireList);
        periodicite.setCategorieOperation(periodiciteDto.getCategorieOperation());
        periodicite.setSousCategorieOperation(periodiciteDto.getSousCategorieOperation());
        periodicite.setLibelle(periodiciteDto.getLibelle());
        periodicite.setSousOperation(periodiciteDto.getSousOperation());
        periodicite.setTypeLigne(periodiciteDto.getTypeLigne());
        periodicite.setTension(periodiciteDto.getTension());
        periodicite.setCategorieMaintenance(periodiciteDto.getCategorieMaintenance());
        periodicite.setUnit(periodiciteDto.getUnit());
        periodicite.setPeriode(periodiciteDto.getPeriode());

        return periodiciteDtoMapper.map(periodiciteRepository.saveAndFlush(periodicite));
    }

    @Override
    public void delete(Long periodiciteId) throws ResourceNotFoundException {
        log.info("Supprimer la periodicite : [ " + periodiciteId + "]");
        periodiciteRepository.deleteById(periodiciteId);
    }
}
