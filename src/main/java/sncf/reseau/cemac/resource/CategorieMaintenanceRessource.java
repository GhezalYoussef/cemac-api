package sncf.reseau.cemac.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sncf.reseau.cemac.dto.CategorieMaintenanceDto;
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

    @Transactional
    @RequestMapping(value = "/update-categorie-maintenance", method = RequestMethod.POST)
    public ResponseEntity<CategorieMaintenanceDto> create(@RequestBody CategorieMaintenanceDto categorieMaintenanceDto) {
        try {
            CategorieMaintenanceDto categorieMaintenanceUpdate = categorieMaintenanceService.update(categorieMaintenanceDto);
            return new ResponseEntity<>(categorieMaintenanceUpdate, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-categorie-maintenance/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        try {
            categorieMaintenanceService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Boolean.TRUE);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Boolean.FALSE);
        }
    }
}
