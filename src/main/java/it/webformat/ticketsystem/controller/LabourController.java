package it.webformat.ticketsystem.controller;

import it.webformat.ticketsystem.data.dto.LabourDto;
import it.webformat.ticketsystem.data.models.Labour;
import it.webformat.ticketsystem.exceptions.IdMustBeNullException;
import it.webformat.ticketsystem.exceptions.IdMustNotBeNullException;
import it.webformat.ticketsystem.service.LabourService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/labours")
public class LabourController {

    private LabourService labourService;

    @GetMapping("/v1")
    @Operation(description = """
            This method is used to retrieve all Labours from database<br>
                """)
    public List<LabourDto> getAllLabours() {
        return labourService.findAll().stream().map(Labour::toDto).toList();
    }

    @PostMapping("/v1")
    @Operation(description = """
            This method is used to insert new Labour<br>
            """)
    public LabourDto createNewLabour(@RequestBody LabourDto labourDto) {
        try {
            Labour labour = labourDto.toModel();
            return labourService.insert(labour).toDto();
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @PutMapping("/v1")
    @Operation(description = """
            This method is used to update existing  Labour<br>
            """)
    public LabourDto updateLabour(@RequestBody LabourDto labourDto) {
        try {
            Labour labour = labourDto.toModel();
            return labourService.update(labour).toDto();
        } catch (IdMustNotBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @DeleteMapping("/v1/{id}")
    @Operation(description = """
            This method is used to delete Labour by id<br>
            """)
    public Boolean deleteLabour(@PathVariable("id") Long id) {
        return labourService.deleteById(id);
    }

    @GetMapping("/v1/{id}")
    @Operation(description = """
            This method is used to find Labour by id<br>
            """)
    public LabourDto getLabourById(@PathVariable("id") Long id) {
        return labourService.findById(id).toDto();
    }



}
