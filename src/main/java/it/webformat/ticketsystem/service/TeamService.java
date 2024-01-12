package it.webformat.ticketsystem.service;

import it.webformat.ticketsystem.data.models.Team;

import java.util.List;

public interface TeamService {

    List<Team> findAll();

    Team insert(Team team);

    Team update(Team team);

    Boolean deleteById(Long id);

    Team findById(Long id);

    Team findTeamByName(String teamName);
}
