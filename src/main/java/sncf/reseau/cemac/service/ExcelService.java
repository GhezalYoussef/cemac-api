package sncf.reseau.cemac.service;

import sncf.reseau.cemac.dto.ExcelPeriodiciteDto;

import java.io.InputStream;

public interface ExcelService {

    ExcelPeriodiciteDto importDonnees(InputStream fis);

}
