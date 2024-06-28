package sncf.reseau.cemac.utils.Excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Component;
import sncf.reseau.cemac.dto.ExcelFamilleCatenaireDto;
import sncf.reseau.cemac.dto.FamilleCatenaireDto;
import sncf.reseau.cemac.enumeration.ELigne;

import java.util.List;

@Component
public class FamilleCatenaireExcelDataReader {


    private final static int ID_FAMILLE_CATENAIRE = 0;
    private final static int TYPE_LIGNE = 1;
    private final static int LIBELLE_FAMILLE_CATENAIRE = 2;

    public static ExcelFamilleCatenaireDto getListFamilleCatenaire(List<List<Object>> donnees) {

        ExcelFamilleCatenaireDto excelFamilleCatenaireDto = new ExcelFamilleCatenaireDto();

        for (int i = 0; i < donnees.size(); i++) {
            try {
                excelFamilleCatenaireDto.getFamilleCatenaireList().add(FamilleCatenaireDto.builder()
                        .id(getIdFamilleCatenaire(donnees.get(i).get(ID_FAMILLE_CATENAIRE), i , ID_FAMILLE_CATENAIRE))
                        .libelle(getLibelleFamilleCatenaire(donnees.get(i).get(LIBELLE_FAMILLE_CATENAIRE), i , LIBELLE_FAMILLE_CATENAIRE))
                        .typeLigne(getTypeLigne(donnees.get(i).get(TYPE_LIGNE), i , TYPE_LIGNE))
                        .build());
            } catch (IllegalArgumentException e) {
                excelFamilleCatenaireDto.getMessageErreurList().add(e.getMessage());
            }
        }
        return excelFamilleCatenaireDto;
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

    private static Long getIdFamilleCatenaire(Object idFamilleCatenaire, int ligne, int column) {
        if (idFamilleCatenaire instanceof Double) {
            return  ((Double) idFamilleCatenaire).longValue();
        } else if (idFamilleCatenaire instanceof String) {
            return Long.valueOf(Integer.valueOf(idFamilleCatenaire.toString()));
        } else {
            throw new IllegalArgumentException(ligne + ":" + column + ": Erreur lors de la lecture de l\'id de la famille catenaire dans le fichier Excel.");
        }
    }

    private static String getLibelleFamilleCatenaire(Object libelleFamilleCatenaire, int ligne, int column) {
        if (libelleFamilleCatenaire instanceof String) {
            return (String) libelleFamilleCatenaire;
        } else {
            throw new IllegalArgumentException(ligne + ":" + column + ": Erreur lors de la lecture du libelle de la famille catenaire dans le fichier Excel.");
        }
    }


    private static ELigne getTypeLigne(Object typeLigne, int ligne, int column) {
        if (typeLigne instanceof String) {
            return ((String) typeLigne).toLowerCase().equals("LGV") ? ELigne.LGV : ELigne.CLASSIQUE;
        } else {
            throw new IllegalArgumentException(ligne + ":" + column + ": Erreur lors de la lecture du type ligne dans le fichier Excel.");
        }
    }

}
