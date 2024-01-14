package it.webformat.ticketsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import it.webformat.ticketsystem.data.dto.ProjectDto;
import it.webformat.ticketsystem.data.models.Project;
import it.webformat.ticketsystem.exceptions.IdMustBeNullException;
import it.webformat.ticketsystem.exceptions.IdMustNotBeNullException;
import it.webformat.ticketsystem.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private ProjectService projectService;

    @GetMapping("/v1")
    @Operation(description = """
            This method is used to retrieve all Projects from database<br>
                """)
    public List<ProjectDto> getAllProjects() {
        return projectService.findAll().stream().map(Project::toDto).toList();
    }

    @PostMapping("/v1")
    @Operation(description = """
            This method is used to insert new Project<br>
            """)
    public ProjectDto createNewProject(@RequestBody ProjectDto projectDto) {
        try {
            Project project = projectDto.toModel();
            return projectService.insert(project).toDto();
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @PutMapping("/v1")
    @Operation(description = """
            This method is used to update existing  Project<br>
            """)
    public ProjectDto updateProject(@RequestBody ProjectDto projectDto) {
        try {
            Project project = projectDto.toModel();
            return projectService.update(project).toDto();
        } catch (IdMustNotBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @DeleteMapping("/v1/{id}")
    @Operation(description = """
            This method is used to delete Project by id<br>
            """)
    public Boolean deleteProject(@PathVariable("id") Long id) {
        return projectService.deleteById(id);
    }

    @GetMapping("/v1/{id}")
    @Operation(description = """
            This method is used to find Project by id<br>
            """)
    public ProjectDto getProjectById(@PathVariable("id") Long id) {
        return projectService.findById(id).toDto();
    }


}
