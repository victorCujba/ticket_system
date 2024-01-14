package it.webformat.ticketsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import it.webformat.ticketsystem.data.dto.CommentsDto;
import it.webformat.ticketsystem.data.models.Comments;
import it.webformat.ticketsystem.exceptions.IdMustBeNullException;
import it.webformat.ticketsystem.exceptions.IdMustNotBeNullException;
import it.webformat.ticketsystem.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentsController {

    private CommentsService commentsService;

    @GetMapping("/v1")
    @Operation(description = """
            This method is used to retrieve all Comments from database<br>
                """)
    public List<CommentsDto> getAllComments() {
        return commentsService.findAll().stream().map(Comments::toDto).toList();
    }

    @PostMapping("/v1")
    @Operation(description = """
            This method is used to insert new Comment<br>
            """)
    public CommentsDto createNewComment(@RequestBody CommentsDto commentsDto) {
        try {
            Comments comments = commentsDto.toModel();
            return commentsService.insert(comments).toDto();
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @PutMapping("/v1")
    @Operation(description = """
            This method is used to update existing  Comment<br>
            """)
    public CommentsDto updateComment(@RequestBody CommentsDto commentsDto) {
        try {
            Comments comments = commentsDto.toModel();
            return commentsService.update(comments).toDto();
        } catch (IdMustNotBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @DeleteMapping("/v1/{id}")
    @Operation(description = """
            This method is used to delete Comment by id<br>
            """)
    public Boolean deleteComment(@PathVariable("id") Long id) {
        return commentsService.deleteById(id);
    }

    @GetMapping("/v1/{id}")
    @Operation(description = """
            This method is used to find Comment by id<br>
            """)
    public CommentsDto getCommentById(@PathVariable("id") Long id) {
        return commentsService.findById(id).toDto();
    }

}
