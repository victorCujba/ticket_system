package it.webformat.ticketsystem.repository;

import it.webformat.ticketsystem.data.models.Comments;
import it.webformat.ticketsystem.data.models.Labour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findCommentsByLabour(Labour labour);
}
