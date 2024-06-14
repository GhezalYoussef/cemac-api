package sncf.reseau.cemac.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sncf.reseau.cemac.dto.ExcelPeriodiciteDto;
import sncf.reseau.cemac.service.ExcelService;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/v1/excel")
public class ExcelResource {

    private final ExcelService excelService;

    @Autowired
    public ExcelResource(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping(value = "/import-donnees")
    public ResponseEntity<?> importDonneesSaisie(@RequestBody byte[] data) {
        try{
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            ExcelPeriodiciteDto excelData = excelService.importDonnees(inputStream);
            return ResponseEntity.ok(excelData);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors du traitement des données Excel: " + e.getMessage());
        }

    }

}
