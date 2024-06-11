package sncf.reseau.cemac.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sncf.reseau.cemac.dto.FamilleCatenaireDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;
import sncf.reseau.cemac.service.FamilleCatenaireService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/famille-catenaire")
public class FamilleCatenaireRessource {

    @Autowired
    private FamilleCatenaireService familleCatenaireService;

    @GetMapping("list-famille-catenaire")
    public ResponseEntity<List<FamilleCatenaireDto>> findAll() throws ResourceNotFoundException {
        return new ResponseEntity<>(familleCatenaireService.getFamilleCatenaireList(), HttpStatus.ACCEPTED);
    }

    @Transactional
    @RequestMapping(value = "/update-famille-catenaire", method = RequestMethod.POST)
    public ResponseEntity<FamilleCatenaireDto> create(@RequestBody FamilleCatenaireDto familleCatenaireDto) {
        try {
            FamilleCatenaireDto familleCatenaireDtoUpdate = familleCatenaireService.update(familleCatenaireDto);
            return new ResponseEntity<>(familleCatenaireDtoUpdate, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-famille-catenaire/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        try {
            familleCatenaireService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Boolean.TRUE);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Boolean.FALSE);
        }
    }

}
