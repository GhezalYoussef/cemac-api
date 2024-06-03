package sncf.reseau.cemac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sncf.reseau.cemac.dto.CatenaireDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;
import sncf.reseau.cemac.mapper.CatenaireDtoMapper;
import sncf.reseau.cemac.repository.CatenaireRepository;
import sncf.reseau.cemac.service.CatenaireService;

import java.util.List;

@Slf4j
@Service
public class CatenaireServiceImpl implements CatenaireService {

    private final CatenaireRepository catenaireRepository;

    private final CatenaireDtoMapper catenaireDtoMapper;

    @Autowired
    public CatenaireServiceImpl(CatenaireRepository catenaireRepository,
                                CatenaireDtoMapper catenaireDtoMapper) {
        this.catenaireRepository = catenaireRepository;
        this.catenaireDtoMapper = catenaireDtoMapper;
    }

    @Override
    public List<CatenaireDto> getListCatenaireList() throws ResourceNotFoundException {
        log.info("Charger la liste des catenaires");
        return catenaireRepository.findAll()
                .stream()
                .map(catenaireDtoMapper::map)
                .toList();
    }

}
