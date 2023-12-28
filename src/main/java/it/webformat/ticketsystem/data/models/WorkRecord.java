package it.webformat.ticketsystem.data.models;

import it.webformat.ticketsystem.data.archetypes.Model;
import it.webformat.ticketsystem.data.dto.WorkRecordDto;
import it.webformat.ticketsystem.enums.TypeOfWorkRecord;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "work_record")
public class WorkRecord implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_badge", nullable = false)
    private Badge badge;

    @Column(name = "time_record")
    private LocalDateTime timeRecord;

    @Enumerated(EnumType.STRING)
    @Column(name = "record_type")
    private TypeOfWorkRecord recordType;

    @Override
    public WorkRecordDto toDto() {
        return WorkRecordDto.builder()
                .id(id)
                .badgeId(badge.getId().toString())
                .timeRecord(timeRecord.toString())
                .recordType(recordType.toString())
                .build();
    }
}
