package sncf.reseau.cemac.utils.Excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Component;
import sncf.reseau.cemac.dto.CatenaireDto;
import sncf.reseau.cemac.dto.ExcelCatenaireDto;
import sncf.reseau.cemac.dto.ExcelPeriodiciteDto;
import sncf.reseau.cemac.dto.PeriodiciteDto;

import java.util.List;

@Component
public class CatenaireExcelDataReader {

    private final static int ID_CATENAIRE = 0;
    private final static int ID_FAMILLE_CATENAIARE = 1;
    private final static int LIBELLE_CATENAIRE = 2;

    public static ExcelCatenaireDto getListCatenaire(List<List<Object>> donnees) {

        ExcelCatenaireDto excelCatenaireDto = new ExcelCatenaireDto();

        for (int i = 0; i < donnees.size(); i++) {
            try {
                excelCatenaireDto.getCatenaireList().add(CatenaireDto.builder()
                        .id(getIdCatenaire(donnees.get(i).get(ID_CATENAIRE), i , ID_CATENAIRE))
                        .familleCatenaire(getIdFamilleCatenaire(donnees.get(i).get(ID_FAMILLE_CATENAIARE), i , ID_FAMILLE_CATENAIARE))
                        .libelle(getLibelleCatenaire(donnees.get(i).get(LIBELLE_CATENAIRE), i , LIBELLE_CATENAIRE))
                        .build());
            } catch (IllegalArgumentException e) {
                excelCatenaireDto.getMessageErreurList().add(e.getMessage());
            }
        }
        return excelCatenaireDto;
    }

    public static Object lireValeurCellule(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private static Long getIdCatenaire(Object idCatenaire, int ligne, int column) {
        if (idCatenaire instanceof Double) {
            return  ((Double) idCatenaire).longValue();
        } else if (idCatenaire instanceof String) {
            return Long.valueOf(Integer.valueOf(idCatenaire.toString()));
        } else {
            throw new IllegalArgumentException(ligne + ":" + column + ": Erreur lors de la lecture de l\'id de la catenaire dans le fichier Excel.");
        }
    }

    private static Long getIdFamilleCatenaire(Object idFamilleCatenaire, int ligne, int column) {
        if (idFamilleCatenaire instanceof Double) {
            return  ((Double) idFamilleCatenaire).longValue();
        } else if (idFamilleCatenaire instanceof String) {
            return Long.valueOf(Integer.valueOf(idFamilleCatenaire.toString()));
        } else {
            throw new IllegalArgumentException(ligne + ":" + column + ": Erreur lors de la lecture de l\'id de la famille catenaire dans le fichier Excel.");
        }
    }

    private static String getLibelleCatenaire(Object libelleCatenaire, int ligne, int column) {
        if (libelleCatenaire instanceof String) {
            return (String) libelleCatenaire;
        } else {
            throw new IllegalArgumentException(ligne + ":" + column + ": Erreur lors de la lecture du libelle de la catenaire dans le fichier Excel.");
        }
    }

}
