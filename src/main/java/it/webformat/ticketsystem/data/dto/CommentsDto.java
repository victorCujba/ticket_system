package it.webformat.ticketsystem.data.dto;

import it.webformat.ticketsystem.data.archetypes.Dto;
import it.webformat.ticketsystem.data.models.Comments;
import it.webformat.ticketsystem.data.models.Labour;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsDto implements Dto {

    private Long id;
    private String body;
    private String labourId;
    private String commentDate;

    @Override
    public Comments toModel() {
        return Comments.builder()
                .id(id)
                .body(body)
                .labour(Labour.builder().id(Long.valueOf(labourId)).build())
                .dateOfComment(LocalDateTime.parse(commentDate))
                .build();
    }
}
