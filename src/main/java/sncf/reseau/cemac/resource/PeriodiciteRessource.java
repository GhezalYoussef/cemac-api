package sncf.reseau.cemac.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sncf.reseau.cemac.dto.PeriodiciteDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;
import sncf.reseau.cemac.service.PeriodiciteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/periodicite")
public class PeriodiciteRessource {

    @Autowired
    private PeriodiciteService periodiciteService;

    @GetMapping("list-periodicite")
    public ResponseEntity<List<PeriodiciteDto>> findAll() throws ResourceNotFoundException {
        return new ResponseEntity<>(periodiciteService.getPeriodiciteList(), HttpStatus.ACCEPTED);
    }

}
