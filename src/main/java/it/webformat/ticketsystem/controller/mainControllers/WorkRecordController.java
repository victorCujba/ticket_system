package it.webformat.ticketsystem.controller.mainControllers;

import io.swagger.v3.oas.annotations.Operation;
import it.webformat.ticketsystem.data.dto.WorkRecordDto;
import it.webformat.ticketsystem.data.models.Badge;
import it.webformat.ticketsystem.data.models.WorkRecord;
import it.webformat.ticketsystem.enums.TypeOfWorkRecord;
import it.webformat.ticketsystem.exceptions.IdMustBeNullException;
import it.webformat.ticketsystem.exceptions.IdMustNotBeNullException;
import it.webformat.ticketsystem.repository.BadgeRepository;
import it.webformat.ticketsystem.service.BadgeService;
import it.webformat.ticketsystem.service.WorkRecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/work-records")
public class WorkRecordController {

    private WorkRecordService workRecordService;
    private BadgeService badgeService;
    private BadgeRepository badgeRepository;

    @PostMapping("/insert-work-records")
    @Operation(description = """
            This method is used to insert work record using badge ID and type of record as parameters.
            """)
    public WorkRecordDto insertWorkingRecord(@RequestParam Long badgeId, @RequestParam TypeOfWorkRecord type) {
        try {
            Optional<Badge> optionalBadge = badgeRepository.findById(badgeId);

            if (optionalBadge.isPresent()) {
                Badge badge = optionalBadge.get();

                List<WorkRecord> workRecordList = badge.getWorkRecordList();
                if (workRecordList == null) {
                    workRecordList = new ArrayList<>();
                }
                WorkRecord workRecord = WorkRecord.builder()
                        .timeRecord(LocalDateTime.now())
                        .recordType(type)
                        .badge(badge)
                        .build();
                workRecordList.add(workRecord);
                badge.setWorkRecordList(workRecordList);

                return workRecordService.insert(workRecord).toDto();
            } else {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Badge with ID: " + badgeId + "not found. Please check badge."
                );
            }
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }


    @GetMapping("/v1")
    @Operation(description = """
            This method is used to retrieve all Work Records from database<br>
                """)
    public List<WorkRecordDto> getAllWorkRecords() {
        return workRecordService.findAll().stream().map(WorkRecord::toDto).toList();
    }

    @PostMapping("/v1")
    @Operation(description = """
            This method is used to insert new Work Record<br>
            """)
    public WorkRecordDto createNewWorkRecord(@RequestBody WorkRecordDto workRecordDto) {
        try {
            WorkRecord workRecord = workRecordDto.toModel();
            return workRecordService.insert(workRecord).toDto();
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @PutMapping("/v1")
    @Operation(description = """
            This method is used to update existing  Work Record<br>
            """)
    public WorkRecordDto updateWorkRecord(@RequestBody WorkRecordDto workRecordDto) {
        try {
            WorkRecord workRecord = workRecordDto.toModel();
            return workRecordService.update(workRecord).toDto();
        } catch (IdMustNotBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @DeleteMapping("/v1/{id}")
    @Operation(description = """
            This method is used to delete Work Record by id<br>
            """)
    public Boolean deleteWorkRecord(@PathVariable("id") Long id) {
        return workRecordService.deleteById(id);
    }

    @GetMapping("/v1/{id}")
    @Operation(description = """
            This method is used to find Work Record by id<br>
            """)
    public WorkRecordDto getWorkRecordById(@PathVariable("id") Long id) {
        return workRecordService.findById(id).toDto();
    }


}
