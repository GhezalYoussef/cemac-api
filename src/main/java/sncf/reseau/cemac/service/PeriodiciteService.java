package sncf.reseau.cemac.service;

import sncf.reseau.cemac.dto.PeriodiciteDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;

import java.util.List;

public interface PeriodiciteService {

    List<PeriodiciteDto> getPeriodiciteList() throws ResourceNotFoundException;
}
