package sncf.reseau.cemac.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sncf.reseau.cemac.dto.UtilisateurDto;
import sncf.reseau.cemac.service.UtilisateurService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/utilisateur")
public class UtilisateurResource {

    @Autowired
    UtilisateurService utilisateurService;

    @Transactional
    @GetMapping(path = "/list-utilisateur")
    public ResponseEntity<List<UtilisateurDto>> getListUtilisateur() {
        try {
            final List<UtilisateurDto> listUtilisateur = utilisateurService.getListUtilisateur();
            if (listUtilisateur.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listUtilisateur, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @RequestMapping(value = "/update-utilisateur", method = RequestMethod.POST)
    public ResponseEntity<UtilisateurDto> create(@RequestBody UtilisateurDto utilisateurDto) {
        try {
            UtilisateurDto utilisateur = utilisateurService.update(utilisateurDto);
            return new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-utilisateur/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        try {
            utilisateurService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Boolean.TRUE);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Boolean.FALSE);
        }
    }

    @GetMapping("/is-utilisateur/{idSNCF}")
    public ResponseEntity<Boolean> isUtilisateur(@PathVariable String idSNCF) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(utilisateurService.isUtilisateur(idSNCF));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Boolean.FALSE);
        }
    }

    @GetMapping("/get-utilisateur/{idSNCF}")
    public ResponseEntity<UtilisateurDto> getUtilisateur(@PathVariable String idSNCF) {
        try {
            final UtilisateurDto utilisateur = utilisateurService.getUtilisateur(idSNCF);
            return new ResponseEntity<>(utilisateur, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
