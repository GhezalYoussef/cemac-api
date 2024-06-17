package sncf.reseau.cemac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sncf.reseau.cemac.dto.CategorieMaintenanceDto;
import sncf.reseau.cemac.entity.CategorieMaintenance;
import sncf.reseau.cemac.exception.ResourceNotFoundException;
import sncf.reseau.cemac.mapper.CategorieMaintenanceDtoMapper;
import sncf.reseau.cemac.repository.CategorieMaintenanceRepository;
import sncf.reseau.cemac.service.CategorieMaintenanceService;

import java.util.List;

@Slf4j
@Service
public class CategorieMaintenanceServiceImpl implements CategorieMaintenanceService {

    private final CategorieMaintenanceRepository categorieMaintenanceRepository;
    private final CategorieMaintenanceDtoMapper categorieMaintenanceDtoMapper;

    @Autowired
    public CategorieMaintenanceServiceImpl(CategorieMaintenanceRepository categorieMaintenanceRepository,
                                           CategorieMaintenanceDtoMapper categorieMaintenanceDtoMapper) {
        this.categorieMaintenanceRepository = categorieMaintenanceRepository;
        this.categorieMaintenanceDtoMapper = categorieMaintenanceDtoMapper;
    }


    @Override
    public List<CategorieMaintenanceDto> getCategorieMaintenanceList() throws ResourceNotFoundException {
        log.info("Charger la liste des categories de maintenance");
        return categorieMaintenanceRepository.findAll()
                .stream()
                .map(categorieMaintenanceDtoMapper::map)
                .toList();
    }

    @Override
    public CategorieMaintenanceDto update(CategorieMaintenanceDto categorieMaintenanceDto) throws ResourceNotFoundException {

        log.info("Modifier la categorie : [" + categorieMaintenanceDto.toString() + "]");
        CategorieMaintenance categorieMaintenance = new CategorieMaintenance();
        categorieMaintenance.setId(categorieMaintenanceDto.getId());
        categorieMaintenance.setTypeLigne(categorieMaintenanceDto.getTypeLigne());
        categorieMaintenance.setPantoMin(categorieMaintenanceDto.getPantoMin());
        categorieMaintenance.setPantoMax(categorieMaintenanceDto.getPantoMax());
        categorieMaintenance.setVitesseMin(categorieMaintenanceDto.getVitesseMin());
        categorieMaintenance.setVitesseMax(categorieMaintenanceDto.getVitesseMax());
        categorieMaintenance.setCategorieMaintenance(categorieMaintenanceDto.getCategorieMaintenance());
        return categorieMaintenanceDtoMapper.map(categorieMaintenanceRepository.saveAndFlush(categorieMaintenance));
    }

    @Override
    public void delete(Long categorieId) throws ResourceNotFoundException {
        log.info("Supprimer la categorie de maintenance : [" + categorieId + "]");
        categorieMaintenanceRepository.deleteById(categorieId);
    }
}
