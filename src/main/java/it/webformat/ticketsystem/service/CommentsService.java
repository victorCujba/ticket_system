package it.webformat.ticketsystem.service;

import it.webformat.ticketsystem.data.models.Comments;

import java.util.List;

public interface CommentsService {

    List<Comments> findAll();

    Comments insert(Comments comments);

    Comments update(Comments comments);

    Boolean deleteById(Long id);

    Comments findById(Long id);


}
