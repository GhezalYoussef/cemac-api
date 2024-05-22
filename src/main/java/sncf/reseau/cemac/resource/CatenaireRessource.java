package sncf.reseau.cemac.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
