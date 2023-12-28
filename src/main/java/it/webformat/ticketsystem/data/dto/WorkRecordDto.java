package it.webformat.ticketsystem.data.dto;

import it.webformat.ticketsystem.data.archetypes.Dto;
import it.webformat.ticketsystem.data.models.Badge;
import it.webformat.ticketsystem.data.models.WorkRecord;
import it.webformat.ticketsystem.enums.TypeOfWorkRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkRecordDto implements Dto {

    private Long id;
    private String badgeId;
    private String timeRecord;
    private String recordType;

    @Override
    public WorkRecord toModel() {
        return WorkRecord.builder()
                .id(id)
                .badge(Badge.builder().id(Long.valueOf(badgeId)).build())
                .timeRecord(LocalDateTime.parse(timeRecord))
                .recordType(TypeOfWorkRecord.valueOf(recordType))
                .build();
    }
}
