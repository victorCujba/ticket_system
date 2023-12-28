package it.webformat.ticketsystem.data.archetypes;


import java.text.ParseException;

public interface Dto {
    Model toModel() throws ParseException;

}
