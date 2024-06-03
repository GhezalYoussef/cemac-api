package sncf.reseau.cemac.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sncf.reseau.cemac.dto.ProfilDto;
import sncf.reseau.cemac.service.ProfilService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/profil")
public class ProfilResource {

    @Autowired
    ProfilService profilService;

    @GetMapping(path = "/list-profil")
    public ResponseEntity<List<ProfilDto>> getProfilList() {
        try {
            final List<ProfilDto> listProfil = profilService.getListProfil();
            if (listProfil.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listProfil, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
