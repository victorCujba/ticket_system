package it.webformat.ticketsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.webformat.ticketsystem.data.dto.CommentsDto;
import it.webformat.ticketsystem.data.models.Comments;
import it.webformat.ticketsystem.data.models.Labour;
import it.webformat.ticketsystem.exceprions.IdMustBeNullException;
import it.webformat.ticketsystem.repository.LabourRepository;
import it.webformat.ticketsystem.service.CommentsService;
import it.webformat.ticketsystem.service.LabourService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@AllArgsConstructor
@RestController
@SecurityRequirement(name = "authentication")
@RequestMapping("/dev")
public class DeveloperController {

    private CommentsService commentsService;
    private LabourRepository labourRepository;
    private LabourService labourService;

    @PostMapping("/insert-comment")
    @Operation(description = """
            This method is used to insert comment to labour<br>
            """)
    public CommentsDto addComment(@RequestBody CommentsDto commentsDto, @RequestParam Long idLabour) {
        try {
            Comments comments = commentsDto.toModel();
            Optional<Labour> labour = labourRepository.findById(idLabour);
            if (labour.isPresent()) {
                Labour labour1 = labour.get();
                labour1.getCommentsList().add(comments);
                labourService.update(labour1);
            }
            return commentsService.insert(comments).toDto();
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

}
