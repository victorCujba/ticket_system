package it.webformat.ticketsystem.service.impl;

import it.webformat.ticketsystem.data.models.Labour;
import it.webformat.ticketsystem.exceptions.IdMustBeNullException;
import it.webformat.ticketsystem.exceptions.IdMustNotBeNullException;
import it.webformat.ticketsystem.repository.LabourRepository;
import it.webformat.ticketsystem.service.LabourService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LabourServiceImpl implements LabourService {

    private LabourRepository labourRepository;

    @Override
    public List<Labour> findAll() {
        return labourRepository.findAll();
    }

    @Override
    public Labour insert(Labour labour) {
        if (labour.getId() != null) {
            throw new IdMustBeNullException();
        }
        return labourRepository.save(labour);
    }

    @Override
    public Labour update(Labour labour) {
        if (labour.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return labourRepository.save(labour);
    }

    @Override
    public Boolean deleteById(Long id) {
        labourRepository.deleteById(id);
        return labourRepository.findById(id).isEmpty();
    }

    @Override
    public Labour findById(Long id) {
        return labourRepository.findById(id).orElse(Labour.builder().build());
    }
}
