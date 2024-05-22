package sncf.reseau.cemac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sncf.reseau.cemac.dto.PeriodiciteDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;
import sncf.reseau.cemac.mapper.PeriodiciteDtoMapper;
import sncf.reseau.cemac.repository.PeriodiciteRepository;
import sncf.reseau.cemac.service.PeriodiciteService;

import java.util.List;

@Slf4j
@Service
public class PeriodiciteServiceImpl implements PeriodiciteService {

    private final PeriodiciteRepository periodiciteRepository;

    private final PeriodiciteDtoMapper periodiciteDtoMapper;

    @Autowired
    public PeriodiciteServiceImpl(PeriodiciteRepository periodiciteRepository,
                                  PeriodiciteDtoMapper periodiciteDtoMapper) {
        this.periodiciteRepository = periodiciteRepository;
        this.periodiciteDtoMapper = periodiciteDtoMapper;
    }

    @Override
    public List<PeriodiciteDto> getPeriodiciteList() throws ResourceNotFoundException {
        log.info("Charger la liste des periodicites");
        return periodiciteRepository.findAll()
                .stream()
                .map(periodiciteDtoMapper::map)
                .toList();
    }
}
