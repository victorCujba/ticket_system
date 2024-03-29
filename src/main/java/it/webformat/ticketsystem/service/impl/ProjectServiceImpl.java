package it.webformat.ticketsystem.service.impl;

import it.webformat.ticketsystem.data.models.Project;
import it.webformat.ticketsystem.exceptions.IdMustBeNullException;
import it.webformat.ticketsystem.exceptions.IdMustNotBeNullException;
import it.webformat.ticketsystem.repository.ProjectRepository;
import it.webformat.ticketsystem.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project insert(Project project) {
        if (project.getId() != null) {
            throw new IdMustBeNullException();
        }
        return projectRepository.save(project);
    }

    @Override
    public Project update(Project project) {
        if (project.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return projectRepository.save(project);
    }

    @Override
    public Boolean deleteById(Long id) {
        projectRepository.deleteById(id);
        return projectRepository.findById(id).isEmpty();
    }

    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id).orElse(Project.builder().build());
    }

    @Override
    public Project findByAssignedPM(String assignedPM) {
        return projectRepository.findByAssignedPM(assignedPM);
    }

    @Override
    public List<Project> getProjectsWithManyTeams() {
        return projectRepository.findAll().stream()
                .filter(project -> project.getTeams().size() > 1)
                .collect(Collectors.toList());
    }
}
