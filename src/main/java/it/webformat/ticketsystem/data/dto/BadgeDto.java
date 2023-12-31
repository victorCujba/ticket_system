package it.webformat.ticketsystem.data.dto;

import it.webformat.ticketsystem.data.archetypes.Dto;
import it.webformat.ticketsystem.data.models.Badge;
import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.data.models.WorkRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static it.webformat.ticketsystem.utility.DataConversionUtils.numberToString;
import static it.webformat.ticketsystem.utility.DataConversionUtils.stringToLong;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BadgeDto implements Dto {

    private Long id;
    private List<WorkRecordDto> workRecordDtoList;
    private String idEmployee;

    @Override
    public Badge toModel() {

        List<WorkRecord> workRecordList;
        if (!(workRecordDtoList == null)) {
            workRecordList = workRecordDtoList.stream()
                    .map(WorkRecordDto::toModel).toList();
        } else {
            workRecordList = new ArrayList<>();
        }

        return Badge.builder()
                .id(id)
                .employee(Employee.builder().id(stringToLong(idEmployee)).build())
                .workRecordList(workRecordList)
                .build();
    }
}
