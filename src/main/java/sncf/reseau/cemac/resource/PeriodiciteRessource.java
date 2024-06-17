package sncf.reseau.cemac.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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

    @Transactional
    @RequestMapping(value = "/update-periodicite", method = RequestMethod.POST)
    public ResponseEntity<PeriodiciteDto> create(@RequestBody PeriodiciteDto periodiciteDto) {
        try {
            PeriodiciteDto periodiciteDtoUpdate = periodiciteService.update(periodiciteDto);
            return new ResponseEntity<>(periodiciteDtoUpdate, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @RequestMapping(value = "/update-periodicite-list", method = RequestMethod.POST)
    public ResponseEntity<List<PeriodiciteDto>> createList(@RequestBody List<PeriodiciteDto> periodiciteDtoList) {
        try {
            List<PeriodiciteDto> periodiciteDtoListUpdate = periodiciteService.updateAll(periodiciteDtoList);
            return new ResponseEntity<>(periodiciteDtoListUpdate, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-periodicite/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        try {
            periodiciteService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Boolean.TRUE);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Boolean.FALSE);
        }
    }

}
