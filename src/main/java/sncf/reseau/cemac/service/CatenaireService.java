package sncf.reseau.cemac.service;

import sncf.reseau.cemac.dto.CatenaireDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;

import java.util.List;

public interface CatenaireService {

    List<CatenaireDto> getListCatenaireList() throws ResourceNotFoundException;
}
