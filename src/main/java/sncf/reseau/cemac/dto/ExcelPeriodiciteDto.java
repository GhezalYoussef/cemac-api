package sncf.reseau.cemac.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelPeriodiciteDto {

    List<PeriodiciteDto> periodiciteList = new ArrayList<>();
    List<String> messageErreurList = new ArrayList<>();

}
