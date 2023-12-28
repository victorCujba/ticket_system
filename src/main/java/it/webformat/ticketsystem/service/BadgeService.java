package it.webformat.ticketsystem.service;

import it.webformat.ticketsystem.data.models.Badge;

import java.util.List;

public interface BadgeService {
    List<Badge> findAll();

    Badge insert(Badge badge);

    Badge update(Badge badge);

    Boolean deleteById(Long id);

    Badge findById(Long id);

}
