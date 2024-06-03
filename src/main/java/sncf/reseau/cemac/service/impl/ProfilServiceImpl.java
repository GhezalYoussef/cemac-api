package sncf.reseau.cemac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sncf.reseau.cemac.dto.ProfilDto;
import sncf.reseau.cemac.exception.ResourceNotFoundException;
import sncf.reseau.cemac.mapper.ProfilDtoMapper;
import sncf.reseau.cemac.repository.ProfilRepository;
import sncf.reseau.cemac.service.ProfilService;

import java.util.List;

@Slf4j
@Service
public class ProfilServiceImpl implements ProfilService {

    ProfilRepository profilRepository;

    ProfilDtoMapper profilDtoMapper;

    @Autowired
    ProfilServiceImpl(ProfilRepository profilRepository,
                      ProfilDtoMapper profilDtoMapper){
        this.profilRepository = profilRepository;
        this.profilDtoMapper = profilDtoMapper;
    }

    @Override
    public List<ProfilDto> getListProfil() throws ResourceNotFoundException {
        log.info("Charger la liste des profils");
        return profilRepository.findAll()
                .stream()
                .map(profilDtoMapper::map)
                .toList();
    }
}
