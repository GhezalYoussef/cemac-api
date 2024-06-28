package sncf.reseau.cemac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sncf.reseau.cemac.dto.CatenaireDto;
import sncf.reseau.cemac.entity.Catenaire;
import sncf.reseau.cemac.entity.FamilleCatenaire;
import sncf.reseau.cemac.exception.ResourceNotFoundException;
import sncf.reseau.cemac.mapper.CatenaireDtoMapper;
import sncf.reseau.cemac.repository.CatenaireRepository;
import sncf.reseau.cemac.repository.FamilleCatenaireRepository;
import sncf.reseau.cemac.service.CatenaireService;

import java.util.List;

@Slf4j
@Service
public class CatenaireServiceImpl implements CatenaireService {

    private final CatenaireRepository catenaireRepository;

    private final FamilleCatenaireRepository familleCatenaireRepository;

    private final CatenaireDtoMapper catenaireDtoMapper;

    @Autowired
    public CatenaireServiceImpl(CatenaireRepository catenaireRepository,
                                FamilleCatenaireRepository familleCatenaireRepository,
                                CatenaireDtoMapper catenaireDtoMapper) {
        this.catenaireRepository = catenaireRepository;
        this.familleCatenaireRepository = familleCatenaireRepository;
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

    @Override
    public CatenaireDto update(CatenaireDto catenaireDto) throws ResourceNotFoundException {

        log.info("Modifeir la catenaire :[ " + catenaireDto.toString() + "]");

        FamilleCatenaire familleCatenaire = familleCatenaireRepository.findById(catenaireDto.getFamilleCatenaire()).orElseThrow();

        Catenaire catenaire = new Catenaire();
        catenaire.setId(catenaireDto.getId());
        catenaire.setFamilleCatenaire(familleCatenaire);
        catenaire.setLibelle(catenaireDto.getLibelle());

        return catenaireDtoMapper.map(catenaireRepository.saveAndFlush(catenaire));
    }

    @Override
    public List<CatenaireDto> updateAll(List<CatenaireDto> catenaireDtoList) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public void delete(Long catenaireId) throws ResourceNotFoundException {

        log.info("Supprimer la catenaire :[" + catenaireId + "]");
        catenaireRepository.deleteById(catenaireId);
    }

}
