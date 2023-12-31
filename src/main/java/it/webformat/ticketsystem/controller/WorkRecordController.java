package it.webformat.ticketsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import it.webformat.ticketsystem.data.dto.WorkRecordDto;
import it.webformat.ticketsystem.data.models.WorkRecord;
import it.webformat.ticketsystem.exceprions.IdMustBeNullException;
import it.webformat.ticketsystem.exceprions.IdMustNotBeNullException;
import it.webformat.ticketsystem.service.WorkRecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/work-records")
public class WorkRecordController {

    private WorkRecordService workRecordService;

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
