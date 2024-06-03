package sncf.reseau.cemac.service;

import sncf.reseau.cemac.dto.UtilisateurDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;

import java.util.List;

public interface UtilisateurService {

    List<UtilisateurDto> getListUtilisateur();

    UtilisateurDto update(UtilisateurDto utilisateurDto) throws ResourceNotFoundException;

    void delete(Long utilisateurId) throws ResourceNotFoundException;

    boolean isUtilisateur(String idSNCF) throws ResourceNotFoundException;

    UtilisateurDto getUtilisateur(String idSNCF) throws ResourceNotFoundException;
}
