package sncf.reseau.cemac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sncf.reseau.cemac.dto.FamilleCatenaireDto;
import sncf.reseau.cemac.entity.FamilleCatenaire;
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

    @Override
    public FamilleCatenaireDto update(FamilleCatenaireDto familleCatenaireDto) throws ResourceNotFoundException {

        log.info("Modifier la famille catenaire : [" + familleCatenaireDto.toString() + "]");

        FamilleCatenaire familleCatenaire = new FamilleCatenaire();
        familleCatenaire.setId(familleCatenaireDto.getId());
        familleCatenaire.setLibelle(familleCatenaireDto.getLibelle());
        familleCatenaire.setTypeLigne(familleCatenaireDto.getTypeLigne());

        return familleCatenaireDtoMapper.map(familleCatenaireRepository.save(familleCatenaire));
    }

    @Override
    public void delete(Long familleCatenaireId) throws ResourceNotFoundException {

        log.info("Supprimer la famille catenaire: [" + familleCatenaireId + "]");
        familleCatenaireRepository.deleteById(familleCatenaireId);
    }
}
