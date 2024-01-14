package it.webformat.ticketsystem.service.impl;

import it.webformat.ticketsystem.data.models.Team;
import it.webformat.ticketsystem.exceptions.IdMustBeNullException;
import it.webformat.ticketsystem.exceptions.IdMustNotBeNullException;
import it.webformat.ticketsystem.repository.TeamRepository;
import it.webformat.ticketsystem.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Team insert(Team team) {
        if (team.getId() != null) {
            throw new IdMustBeNullException();
        }
        return teamRepository.save(team);
    }

    @Override
    public Team update(Team team) {
        if (team.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return teamRepository.save(team);
    }

    @Override
    public Boolean deleteById(Long id) {
        teamRepository.deleteById(id);
        return teamRepository.findById(id).isEmpty();
    }

    @Override
    public Team findById(Long id) {
        return teamRepository.findById(id).orElse(Team.builder().build());
    }

    @Override
    public Team findTeamByName(String name) {
        return teamRepository.findByName(name);
    }
}
