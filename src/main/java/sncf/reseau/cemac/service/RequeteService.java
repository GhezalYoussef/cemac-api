package sncf.reseau.cemac.service;

import sncf.reseau.cemac.dto.RequeteDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;

import java.util.List;

public interface RequeteService {

    List<RequeteDto> getAll() throws ResourceNotFoundException;
    RequeteDto getById(Long id) throws ResourceNotFoundException;
    RequeteDto update(RequeteDto requeteDto);
    void delete(Long id) throws ResourceNotFoundException;
    RequeteDto getAnalyseResult(RequeteDto requeteDto);
    List<RequeteDto> getAnalyseResultList(List<RequeteDto> requeteDtoList);
}
