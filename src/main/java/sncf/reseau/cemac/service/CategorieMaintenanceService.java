package sncf.reseau.cemac.service;

import sncf.reseau.cemac.dto.CategorieMaintenanceDto;
import sncf.reseau.cemac.entity.CategorieMaintenance;
import sncf.reseau.cemac.exception.ResourceNotFoundException;

import java.util.List;

public interface CategorieMaintenanceService {

   List<CategorieMaintenanceDto> getCategorieMaintenanceList() throws ResourceNotFoundException;

}
