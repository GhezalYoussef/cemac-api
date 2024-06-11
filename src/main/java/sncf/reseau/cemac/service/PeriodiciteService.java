package sncf.reseau.cemac.service;

import sncf.reseau.cemac.dto.PeriodiciteDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;

import java.util.List;

public interface PeriodiciteService {

    List<PeriodiciteDto> getPeriodiciteList() throws ResourceNotFoundException;

    PeriodiciteDto update(PeriodiciteDto periodiciteDto) throws ResourceNotFoundException;

    void delete(Long periodiciteId) throws ResourceNotFoundException;
}
