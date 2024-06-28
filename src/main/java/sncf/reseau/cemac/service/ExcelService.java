package sncf.reseau.cemac.service;

import sncf.reseau.cemac.dto.ExcelCatenaireDto;
import sncf.reseau.cemac.dto.ExcelFamilleCatenaireDto;
import sncf.reseau.cemac.dto.ExcelPeriodiciteDto;

import java.io.InputStream;

public interface ExcelService {

    ExcelPeriodiciteDto importDonneesPeriodicite(InputStream fis);

    ExcelCatenaireDto importDonneesCatenaire(InputStream fis);

    ExcelFamilleCatenaireDto importDonneesFamilleCatenaire(InputStream fis);

}
