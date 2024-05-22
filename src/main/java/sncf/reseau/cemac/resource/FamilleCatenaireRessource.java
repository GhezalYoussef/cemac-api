package sncf.reseau.cemac.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sncf.reseau.cemac.dto.FamilleCatenaireDto;
import sncf.reseau.cemac.entity.FamilleCatenaire;
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

}
