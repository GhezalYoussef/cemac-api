package sncf.reseau.cemac.service;

import sncf.reseau.cemac.dto.FamilleCatenaireDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;

import java.util.List;

public interface FamilleCatenaireService {

    List<FamilleCatenaireDto> getFamilleCatenaireList() throws ResourceNotFoundException;
}
