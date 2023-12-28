package it.webformat.ticketsystem.service;

import it.webformat.ticketsystem.data.models.WorkRecord;

import java.util.List;

public interface WorkRecordService {
    List<WorkRecord> findAll();

    WorkRecord insert(WorkRecord workRecord);

    WorkRecord update(WorkRecord workRecord);

    Boolean deleteById(Long id);

    WorkRecord findById(Long id);
}
