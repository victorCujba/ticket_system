package it.webformat.ticketsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.webformat.ticketsystem.data.dto.LabourDto;
import it.webformat.ticketsystem.data.models.Labour;
import it.webformat.ticketsystem.exceprions.IdMustBeNullException;
import it.webformat.ticketsystem.exceprions.IdMustNotBeNullException;
import it.webformat.ticketsystem.service.LabourService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@RestController
@SecurityRequirement(name = "authentication")
@RequestMapping("/project-manager")
public class ProjectManagerController {

    private LabourService labourService;

    @PostMapping("/create-labour")
    @Operation(description = """
            This method is used to insert by Project Manager new Labour<br>
            """)
    public LabourDto createLabour(@RequestBody LabourDto labourDto) {
        try {
            Labour labour = labourDto.toModel();
            return labourService.insert(labour).toDto();
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @PutMapping("/update-labour")
    @Operation(description = """
            This method is used to update by Project Manager existing  Labour<br>
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

}
