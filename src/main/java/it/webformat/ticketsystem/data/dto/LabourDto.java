package it.webformat.ticketsystem.data.dto;

import it.webformat.ticketsystem.data.archetypes.Dto;
import it.webformat.ticketsystem.data.models.Comments;
import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.data.models.Labour;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabourDto implements Dto {

    private Long id;
    private String desc;
    private String deadline;
    private String devId;
    private List<CommentsDto> commentsDtoList;

    @Override
    public Labour toModel() {

        List<Comments> commentsList;
        if (!commentsDtoList.isEmpty()) {
            commentsList = commentsDtoList.stream()
                    .map(CommentsDto::toModel).toList();
        } else {
            commentsList = new ArrayList<>();
        }

        return Labour.builder()
                .id(id)
                .description(desc)
                .deadline(LocalDate.parse(deadline))
                .employee(Employee.builder().id(Long.valueOf(devId)).build())
                .commentsList(commentsList)
                .build();
    }
}
