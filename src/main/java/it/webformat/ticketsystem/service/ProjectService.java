package it.webformat.ticketsystem.service;

import it.webformat.ticketsystem.data.models.Project;

import java.util.List;

public interface ProjectService {

    List<Project> findAll();

    Project insert(Project project);

    Project update(Project project);

    Boolean deleteById(Long id);

    Project findById(Long id);

}
