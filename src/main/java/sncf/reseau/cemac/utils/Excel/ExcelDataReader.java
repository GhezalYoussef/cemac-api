package sncf.reseau.cemac.utils.Excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Component;
import sncf.reseau.cemac.dto.CatenaireDto;
import sncf.reseau.cemac.dto.ExcelPeriodiciteDto;
import sncf.reseau.cemac.dto.PeriodiciteDto;
import sncf.reseau.cemac.enumeration.ELigne;
import sncf.reseau.cemac.enumeration.ETension;
import sncf.reseau.cemac.enumeration.EUnit;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelDataReader {

    private final static int CATEGORIE = 1;
    private final static int SOUS_CATEGORIE = 2;
    private final static int OPERATION = 3;
    private final static int SOUS_OPERATION = 4;
    private final static int TYPE_LIGNE = 5;
    private final static int TENSION = 6;
    private final static int TYPE_CATENAIRE = 7;
    private final static int CATEGORIE_MAINTENANCE = 8;
    private final static int UNIT = 9;
    private final static int PERIODE = 10;

    public static ExcelPeriodiciteDto getListPeriodicite(List<List<Object>> donnees, List<CatenaireDto> catenaireDtoList) {

        ExcelPeriodiciteDto excelPeriodiciteDto = new ExcelPeriodiciteDto();

        for (int i = 0; i < donnees.size(); i++) {
            try {
                excelPeriodiciteDto.getPeriodiciteDtoList().add(PeriodiciteDto.builder()
                                .categorieOperation(getCategorieOperation(donnees.get(i).get(CATEGORIE), i , CATEGORIE))
                                .sousCategorieOperation(getSousCategorieOperation(donnees.get(i).get(SOUS_CATEGORIE), i , SOUS_CATEGORIE))
                                .libelle(getOperation(donnees.get(i).get(OPERATION), i , OPERATION))
                                .sousOperation(getSousOperation(donnees.get(i).get(SOUS_OPERATION), i , SOUS_OPERATION))
                                .typeLigne(getTypeLigne(donnees.get(i).get(TYPE_LIGNE), i , TYPE_LIGNE))
                                .tension(getTension(donnees.get(i).get(TENSION), i , TENSION))
                                .catenaires(getListCatenaire(donnees.get(i).get(TYPE_CATENAIRE), catenaireDtoList , i , TYPE_CATENAIRE))
                                .categorieMaintenance(getCategorieMaintenance(donnees.get(i).get(CATEGORIE_MAINTENANCE), i , CATEGORIE_MAINTENANCE))
                                .unit(getUnite(donnees.get(i).get(UNIT), i , UNIT))
                                .periode(getPeriode(donnees.get(i).get(PERIODE), i , PERIODE))


                        .build());
            } catch (IllegalArgumentException e) {
                excelPeriodiciteDto.getMessageErreurList().add(e.getMessage());
            }
        }
        return excelPeriodiciteDto;
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

    private static String getCategorieOperation(Object categorieOperation, int ligne, int column) {
        if (categorieOperation instanceof String) {
            return (String) categorieOperation;
        } else {
            throw new IllegalArgumentException(ligne + ":" + column + ": Erreur lors de la lecture du la catégorie opération dans le fichier Excel.");
        }
    }

    private static String getSousCategorieOperation(Object sousCategorieOperation, int ligne, int column) {
        if (sousCategorieOperation instanceof String) {
            return (String) sousCategorieOperation;
        } else {
            throw new IllegalArgumentException(ligne + ":" + column + ": Erreur lors de la lecture du la sous catégorie opération dans le fichier Excel.");
        }
    }

    private static String getOperation(Object operation, int ligne, int column) {
        if (operation instanceof String) {
            return (String) operation;
        } else {
            throw new IllegalArgumentException(ligne + ":" + column + ": Erreur lors de la lecture du l\'opération dans le fichier Excel.");
        }
    }

    private static String getSousOperation(Object sousOperation, int ligne, int column) {
        if (sousOperation instanceof String) {
            return (String) sousOperation;
        } else {
            throw new IllegalArgumentException(ligne + ":" + column + ": Erreur lors de la lecture sous opération dans le fichier Excel.");
        }
    }

    private static ELigne getTypeLigne(Object typeLigne, int ligne, int column) {
        if (typeLigne instanceof String) {
            return ((String) typeLigne).toLowerCase().equals("LGV") ? ELigne.LGV : ELigne.CLASSIQUE;
        } else {
            throw new IllegalArgumentException(ligne + ":" + column + ": Erreur lors de la lecture du type ligne dans le fichier Excel.");
        }
    }

    private static ETension getTension(Object tension, int ligne, int column) {
        if (tension instanceof String) {
            return ((String) tension).toLowerCase().equals("1500") ? ETension._1500 : ETension._25000;
        } else {
            throw new IllegalArgumentException(ligne + ":" + column + ": Erreur lors de la lecture du la coloumn tension dans le fichier Excel.");
        }
    }

    private static List<CatenaireDto> getListCatenaire(Object catenaireIdString , List<CatenaireDto> catenaireDtoList,int ligne, int column) {
        List<CatenaireDto> catenaireDtoListUpdate = new ArrayList<>();
        if(catenaireIdString instanceof String) {
            List<Integer> catenaireIdList = convertStringToListOfIdCatenaire(catenaireIdString.toString());
            catenaireIdList.forEach(
                    idCatenaire -> {
                        CatenaireDto catenaireDto = findCatenaireById(catenaireDtoList, Long.valueOf(idCatenaire));
                        if(catenaireDto != null){
                            catenaireDtoListUpdate.add(findCatenaireById(catenaireDtoList, Long.valueOf(idCatenaire)));
                        }
                    });
        }

        return catenaireDtoListUpdate;
    }

    private static String getCategorieMaintenance(Object typeCategorie, int ligne, int column) {
        if (typeCategorie instanceof String) {
            return (String) typeCategorie;
        } else {
            throw new IllegalArgumentException(ligne + ":" + column + ": Erreur lors de la lecture du type de la catégorie dans le fichier Excel.");
        }
    }

    private static EUnit getUnite(Object unite, int ligne, int column) {
        if (unite instanceof String) {
            return ((String) unite).toLowerCase().equals("ans") ? EUnit.ANS :
                    ((String) unite).toLowerCase().equals("mois") ? EUnit.MOIS :
                            EUnit.JOURS;
        } else {
            throw new IllegalArgumentException(ligne + ":" + column + ": Erreur lors de la lecture du la coloumn unite dans le fichier Excel.");
        }
    }

    private static Integer getPeriode(Object periode, int ligne, int column) {
        if (periode instanceof Integer) {
            return Integer.valueOf(periode.toString());
        } else if (periode instanceof String) {
            return Integer.valueOf(Integer.valueOf(periode.toString()));
        } else {
            throw new IllegalArgumentException(ligne + ":" + column + ": Erreur lors de la lecture de la periode dans le fichier Excel.");
        }
    }

    public static List<Integer> convertStringToListOfIdCatenaire(String str) {
        String[] stringArray = str.split(",");
        List<Integer> listOfIdCatenaire = new ArrayList<>();
        for (String number : stringArray) {
            listOfIdCatenaire.add(Integer.parseInt(number));
        }
        return listOfIdCatenaire;
    }

    public static CatenaireDto findCatenaireById(List<CatenaireDto> catenaireDtoList, Long id) {
        for (CatenaireDto catenaireDto : catenaireDtoList) {
            if (catenaireDto.getId().equals(id)) {
                return catenaireDto;
            }
        }
        return null;
    }
}
