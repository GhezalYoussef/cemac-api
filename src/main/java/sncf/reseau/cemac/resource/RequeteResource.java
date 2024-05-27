package sncf.reseau.cemac.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sncf.reseau.cemac.dto.RequeteDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;
import sncf.reseau.cemac.service.RequeteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/requete")
public class RequeteResource {

    @Autowired
    private RequeteService requeteService;

    @Transactional
    @PostMapping(path = "/update")
    public ResponseEntity<RequeteDto> saveRequete(@RequestBody RequeteDto requeteDto){
        return new ResponseEntity<>(requeteService.update(requeteDto), HttpStatus.ACCEPTED);
    }

    @Transactional
    @GetMapping("list-requete")
    public ResponseEntity<List<RequeteDto>> findAll() throws ResourceNotFoundException {
        return new ResponseEntity<>(requeteService.getAll(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequeteDto> getById(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(requeteService.getById(id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity suppressionRequete(@RequestParam Long idRequete){
        requeteService.delete(idRequete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/analyse")
    public ResponseEntity<RequeteDto> analyseRequete(@RequestBody RequeteDto requeteDto){
        return new ResponseEntity<>(requeteService.getAnalyseResult(requeteDto), HttpStatus.ACCEPTED);
    }
}
