package it.webformat.ticketsystem.service.impl;

import it.webformat.ticketsystem.data.models.Comments;
import it.webformat.ticketsystem.exceptions.IdMustBeNullException;
import it.webformat.ticketsystem.exceptions.IdMustNotBeNullException;
import it.webformat.ticketsystem.repository.CommentsRepository;
import it.webformat.ticketsystem.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentsServiceImpl implements CommentsService {

    private CommentsRepository commentsRepository;

    @Override
    public List<Comments> findAll() {
        return commentsRepository.findAll();
    }

    @Override
    public Comments insert(Comments comments) {
        if (comments.getId() != null) {
            throw new IdMustBeNullException();
        }
        return commentsRepository.save(comments);
    }

    @Override
    public Comments update(Comments comments) {
        if (comments.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return commentsRepository.save(comments);
    }

    @Override
    public Boolean deleteById(Long id) {
        commentsRepository.deleteById(id);
        return commentsRepository.findById(id).isEmpty();
    }

    @Override
    public Comments findById(Long id) {
        return commentsRepository.findById(id).orElse(Comments.builder().build());
    }
}
