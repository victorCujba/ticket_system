package it.webformat.ticketsystem.service;

import it.webformat.ticketsystem.data.models.Labour;

import java.util.List;

public interface LabourService {


    List<Labour> findAll();

    Labour insert(Labour project);

    Labour update(Labour project);

    Boolean deleteById(Long id);

    Labour findById(Long id);


}
