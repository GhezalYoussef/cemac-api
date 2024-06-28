package sncf.reseau.cemac.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sncf.reseau.cemac.entity.FamilleCatenaire;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelFamilleCatenaireDto {

    List<FamilleCatenaireDto> familleCatenaireList = new ArrayList<>();
    List<String> messageErreurList = new ArrayList<>();
}
