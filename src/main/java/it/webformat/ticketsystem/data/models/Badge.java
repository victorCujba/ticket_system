package it.webformat.ticketsystem.data.models;

import it.webformat.ticketsystem.data.archetypes.Model;
import it.webformat.ticketsystem.data.dto.BadgeDto;
import it.webformat.ticketsystem.data.dto.WorkRecordDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "badge")
public class Badge implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "badge")
    private List<WorkRecord> workRecordList;

    @OneToOne
    @JoinColumn(name = "id_employee", referencedColumnName = "id")
    private Employee employee;

    @Override
    public BadgeDto toDto() {
        List<WorkRecordDto> workRecordDtoList;
        if (!workRecordList.isEmpty()) {
            workRecordDtoList = workRecordList.stream()
                    .map(WorkRecord::toDto).toList();
        } else {
            workRecordDtoList = new ArrayList<>();
        }


        return BadgeDto.builder()
                .id(id)
                .idEmployee(employee.getId().toString())
                .workRecordDtoList(workRecordDtoList)
                .build();
    }
}
