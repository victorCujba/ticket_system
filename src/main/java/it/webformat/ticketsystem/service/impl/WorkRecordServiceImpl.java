package it.webformat.ticketsystem.service.impl;

import it.webformat.ticketsystem.data.models.WorkRecord;
import it.webformat.ticketsystem.exceprions.IdMustBeNullException;
import it.webformat.ticketsystem.exceprions.IdMustNotBeNullException;
import it.webformat.ticketsystem.repository.WorkRecordRepository;
import it.webformat.ticketsystem.service.WorkRecordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class WorkRecordServiceImpl implements WorkRecordService {

    private WorkRecordRepository workRecordRepository;

    @Override
    public List<WorkRecord> findAll() {
        return workRecordRepository.findAll();
    }

    @Override
    public WorkRecord insert(WorkRecord workRecord) {
        if (workRecord.getId() != null) {
            throw new IdMustBeNullException();
        }
        return workRecordRepository.save(workRecord);
    }

    @Override
    public WorkRecord update(WorkRecord workRecord) {
        if (workRecord.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return workRecordRepository.save(workRecord);
    }

    @Override
    public Boolean deleteById(Long id) {
        workRecordRepository.deleteById(id);
        return workRecordRepository.findById(id).isEmpty();
    }

    @Override
    public WorkRecord findById(Long id) {
        return workRecordRepository.findById(id).orElse(WorkRecord.builder().build());
    }
}
