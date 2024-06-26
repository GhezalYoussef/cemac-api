package sncf.reseau.cemac.service;

import sncf.reseau.cemac.dto.CatenaireDto;
import sncf.reseau.cemac.dto.FamilleCatenaireDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;

import java.util.List;

public interface FamilleCatenaireService {

    List<FamilleCatenaireDto> getFamilleCatenaireList() throws ResourceNotFoundException;

    FamilleCatenaireDto update(FamilleCatenaireDto familleCatenaireDto) throws ResourceNotFoundException;

    List<FamilleCatenaireDto> updateAll(List<FamilleCatenaireDto> familleCatenaireDtoList) throws ResourceNotFoundException;

    void delete(Long familleCatenaireId) throws ResourceNotFoundException;
}
