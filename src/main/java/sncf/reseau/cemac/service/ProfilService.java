package sncf.reseau.cemac.service;

import sncf.reseau.cemac.dto.ProfilDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;

import java.util.List;

public interface ProfilService {

    List<ProfilDto> getListProfil() throws ResourceNotFoundException;
}
