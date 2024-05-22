package sncf.reseau.cemac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sncf.reseau.cemac.dto.FamilleCatenaireDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;
import sncf.reseau.cemac.mapper.FamilleCatenaireDtoMapper;
import sncf.reseau.cemac.repository.FamilleCatenaireRepository;
import sncf.reseau.cemac.service.FamilleCatenaireService;

import java.util.List;

@Slf4j
@Service
public class FamilleCatenaireServiceImpl implements FamilleCatenaireService {

    private final FamilleCatenaireRepository familleCatenaireRepository;

    private final FamilleCatenaireDtoMapper familleCatenaireDtoMapper;

    @Autowired
    public FamilleCatenaireServiceImpl(FamilleCatenaireRepository familleCatenaireRepository,
                                       FamilleCatenaireDtoMapper familleCatenaireDtoMapper) {
        this.familleCatenaireRepository = familleCatenaireRepository;
        this.familleCatenaireDtoMapper = familleCatenaireDtoMapper;
    }


    @Override
    public List<FamilleCatenaireDto> getFamilleCatenaireList() throws ResourceNotFoundException {
        log.info("Charger la liste des familles de catenaire");
        return familleCatenaireRepository.findAll()
                .stream()
                .map(familleCatenaireDtoMapper::map)
                .toList();
    }
}
