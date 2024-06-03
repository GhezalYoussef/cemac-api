package sncf.reseau.cemac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sncf.reseau.cemac.dto.UtilisateurDto;
import sncf.reseau.cemac.entity.Profil;
import sncf.reseau.cemac.entity.Utilisateur;
import sncf.reseau.cemac.exception.ResourceNotFoundException;
import sncf.reseau.cemac.mapper.UtilisateurDtoMapper;
import sncf.reseau.cemac.repository.ProfilRepository;
import sncf.reseau.cemac.repository.UtilisateurRepository;
import sncf.reseau.cemac.service.UtilisateurService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UtilisateurServiceImpl implements UtilisateurService {


    UtilisateurRepository utilisateurRepository;

    UtilisateurDtoMapper utilisateurDtoMapper;

    ProfilRepository profilRepository;

    @Autowired
    UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository,
                           ProfilRepository profilRepository,
                           UtilisateurDtoMapper utilisateurDtoMapper
                           ){
        this.utilisateurRepository = utilisateurRepository;
        this.profilRepository = profilRepository;
        this.utilisateurDtoMapper = utilisateurDtoMapper;
    }

    @Override
    public List<UtilisateurDto> getListUtilisateur() {
        log.info("Charger la liste des utilisateurs");
        return utilisateurRepository.findAll()
                .stream()
                .map(utilisateurDtoMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public UtilisateurDto update(UtilisateurDto utilisateurDto) throws ResourceNotFoundException {

        Profil profil = profilRepository.findById(utilisateurDto.getProfil().getId()).orElseThrow();

        log.info("Modifier l'utilisateur : [" + utilisateurDto.toString() + "]");
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setProfil(profil);
        utilisateur.setIdSncf(utilisateurDto.getIdSncf().toUpperCase());
        return utilisateurDtoMapper.map(utilisateurRepository.saveAndFlush(utilisateur));
    }

    @Override
    public void delete(Long utilisateurId) throws ResourceNotFoundException {
        log.info("Supprimer l'utilisateur : [" + utilisateurId + "]");
        utilisateurRepository.deleteById(utilisateurId);
    }

    @Override
    public boolean isUtilisateur(String idSNCF) throws ResourceNotFoundException {
        log.info("Verifier utilisateur avec idSNCF: [" + idSNCF + "]");
        final Utilisateur utilisateur = utilisateurRepository.findUtilisateurByIdSncf(idSNCF);
        if(utilisateur != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public UtilisateurDto getUtilisateur(String idSNCF) throws ResourceNotFoundException {
        log.info("Charger l'utilisateur avec l'id [" + idSNCF + "]");
        return utilisateurDtoMapper.map(utilisateurRepository.findUtilisateurByIdSncf(idSNCF));
    }
}
