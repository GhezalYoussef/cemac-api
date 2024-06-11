package sncf.reseau.cemac.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sncf.reseau.cemac.dto.CatenaireDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;
import sncf.reseau.cemac.service.CatenaireService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catenaire")
public class CatenaireRessource {

    @Autowired
    private CatenaireService catenaireService;

    @GetMapping("list-catenaire")
    public ResponseEntity<List<CatenaireDto>> findAll() throws ResourceNotFoundException {
        return new ResponseEntity<>(catenaireService.getListCatenaireList(), HttpStatus.ACCEPTED);
    }

    @Transactional
    @RequestMapping(value = "/update-catenaire", method = RequestMethod.POST)
    public ResponseEntity<CatenaireDto> create(@RequestBody CatenaireDto catenaireDto) {
        try {
            CatenaireDto catenaireUpdate = catenaireService.update(catenaireDto);
            return new ResponseEntity<>(catenaireUpdate, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-catenaire/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        try {
            catenaireService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Boolean.TRUE);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Boolean.FALSE);
        }
    }
}
