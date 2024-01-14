package it.webformat.ticketsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import it.webformat.ticketsystem.data.dto.TeamDto;
import it.webformat.ticketsystem.data.models.Team;
import it.webformat.ticketsystem.exceptions.IdMustBeNullException;
import it.webformat.ticketsystem.exceptions.IdMustNotBeNullException;
import it.webformat.ticketsystem.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/teams")
public class TeamController {

    private TeamService teamService;

    @GetMapping("/v1")
    @Operation(description = """
            This method is used to retrieve all Teams from database<br>
                """)
    public List<TeamDto> getAllTeams() {
        return teamService.findAll().stream().map(Team::toDto).toList();
    }

    @PostMapping("/v1")
    @Operation(description = """
            This method is used to insert new Team in database<br>
            """)
    public TeamDto createNewTeam(@RequestBody TeamDto teamDto) {
        try {
            Team team = teamDto.toModel();
            return teamService.insert(team).toDto();
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @PutMapping("/v1")
    @Operation(description = """
            This method is used to update existing  Team<br>
            """)
    public TeamDto updateTeam(@RequestBody TeamDto teamDto) {
        try {
            Team team = teamDto.toModel();
            return teamService.update(team).toDto();
        } catch (IdMustNotBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @DeleteMapping("/v1/{id}")
    @Operation(description = """
            This method is used to delete Team by id<br>
            """)
    public Boolean deleteTeam(@PathVariable("id") Long id) {
        return teamService.deleteById(id);
    }

    @GetMapping("/v1/{id}")
    @Operation(description = """
            This method is used to find Team by id<br>
            """)
    public TeamDto getLabourById(@PathVariable("id") Long id) {
        return teamService.findById(id).toDto();
    }

}
