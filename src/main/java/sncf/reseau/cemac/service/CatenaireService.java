package sncf.reseau.cemac.service;

import sncf.reseau.cemac.dto.CatenaireDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;

import java.util.List;

public interface CatenaireService {

    List<CatenaireDto> getListCatenaireList() throws ResourceNotFoundException;

    CatenaireDto update(CatenaireDto catenaireDto) throws ResourceNotFoundException;

    void delete(Long catenaireId) throws ResourceNotFoundException;
}
