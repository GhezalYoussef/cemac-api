package sncf.reseau.cemac.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sncf.reseau.cemac.dto.CategorieMaintenanceDto;
import sncf.reseau.cemac.entity.CategorieMaintenance;
import sncf.reseau.cemac.exception.ResourceNotFoundException;
import sncf.reseau.cemac.service.CategorieMaintenanceService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorie-maintenance")
public class CategorieMaintenanceRessource {

    @Autowired
    private CategorieMaintenanceService categorieMaintenanceService;

    @GetMapping("list-categorie-maintenance")
    public ResponseEntity<List<CategorieMaintenanceDto>> findAll() throws ResourceNotFoundException{
       return new ResponseEntity<>(categorieMaintenanceService.getCategorieMaintenanceList(), HttpStatus.ACCEPTED);
    }
}
