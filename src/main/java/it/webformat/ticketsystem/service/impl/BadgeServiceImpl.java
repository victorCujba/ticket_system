package it.webformat.ticketsystem.service.impl;

import it.webformat.ticketsystem.data.models.Badge;
import it.webformat.ticketsystem.exceprions.IdMustBeNullException;
import it.webformat.ticketsystem.exceprions.IdMustNotBeNullException;
import it.webformat.ticketsystem.repository.BadgeRepository;
import it.webformat.ticketsystem.service.BadgeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BadgeServiceImpl implements BadgeService {

    private BadgeRepository badgeRepository;

    @Override
    public List<Badge> findAll() {
        return badgeRepository.findAll();
    }

    @Override
    public Badge insert(Badge badge) {
        if (badge.getId() != null) {
            throw new IdMustBeNullException();
        }
        return badgeRepository.save(badge);
    }

    @Override
    public Badge update(Badge badge) {
        if (badge.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return badgeRepository.save(badge);
    }

    @Override
    public Boolean deleteById(Long id) {
        badgeRepository.deleteById(id);
        return badgeRepository.findById(id).isEmpty();
    }

    @Override
    public Badge findById(Long id) {
        return badgeRepository.findById(id).orElse(Badge.builder().build());
    }
}
