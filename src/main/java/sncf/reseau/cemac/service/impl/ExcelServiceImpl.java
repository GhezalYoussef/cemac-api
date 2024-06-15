package sncf.reseau.cemac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sncf.reseau.cemac.dto.ExcelPeriodiciteDto;
import sncf.reseau.cemac.mapper.CatenaireDtoMapper;
import sncf.reseau.cemac.repository.CatenaireRepository;
import sncf.reseau.cemac.service.ExcelService;
import sncf.reseau.cemac.utils.Excel.ExcelDataReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService {

    private final int SOME_FIXED_NUMBER = 11;

    private final CatenaireRepository catenaireRepository;

    private final CatenaireDtoMapper catenaireDtoMapper;

    @Autowired
    private ExcelServiceImpl(CatenaireRepository catenaireRepository,
                             CatenaireDtoMapper catenaireDtoMapper){
        this.catenaireRepository = catenaireRepository;
        this.catenaireDtoMapper = catenaireDtoMapper;
    }

    @Override
    public ExcelPeriodiciteDto importDonnees(InputStream fis) {
        log.info("Charger la liste des données saisies");
        List<List<Object>> donnees = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            boolean premiereLigne = true;

            for (Row row : sheet) {
                if (premiereLigne) {
                    // Ignorer la première ligne (en-têtes)
                    premiereLigne = false;
                    continue;
                }

                List<Object> donneesDeLaLigne = new ArrayList<>();

                // Itérer jusqu'au dernier indice de colonne possible dans la ligne
                int lastColumn = Math.max(row.getLastCellNum(), SOME_FIXED_NUMBER); // SOME_FIXED_NUMBER devrait être le nombre de colonnes attendu
                for (int cn = 0; cn < lastColumn; cn++) {
                    Cell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    Object valeur;
                    if (cell.getCellType() == CellType.BLANK) {
                        // Traitement des cellules vides comme chaîne vide
                        valeur = "";
                    } else {
                        // Lire la valeur de la cellule
                        valeur = ExcelDataReader.lireValeurCellule(cell);
                    }
                    donneesDeLaLigne.add(valeur);
                }

                donnees.add(donneesDeLaLigne);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ExcelDataReader.getListPeriodicite(
                donnees,
                catenaireRepository.findAll()
                    .stream()
                    .map(catenaireDtoMapper::map)
                    .toList()
        );
    }


}
