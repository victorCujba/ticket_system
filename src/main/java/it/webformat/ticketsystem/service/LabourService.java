package it.webformat.ticketsystem.service;

import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.data.models.Labour;
import it.webformat.ticketsystem.data.models.Project;
import it.webformat.ticketsystem.enums.TaskStatus;

import java.util.List;

public interface LabourService {


    List<Labour> findAll();

    Labour insert(Labour project);

    Labour update(Labour project);

    Boolean deleteById(Long id);

    Labour findById(Long id);

    Labour findByEmployeeAndProjectAndStatus(Employee employee, Project project, TaskStatus taskStatus);
}
