package it.webformat.ticketsystem.data.dto;

import it.webformat.ticketsystem.data.models.Comments;
import it.webformat.ticketsystem.data.models.Labour;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LabourWithCommentsDto {

    private Long labourId;
    private LocalDate deadline;
    private List<CommentsDto> comments;
    private Long developerId;
    private String developerFullName;

    public static LabourWithCommentsDto fromEntity(Labour labour) {
        List<CommentsDto> commentsDtoList;
        if (labour.getCommentsList() != null) {
            commentsDtoList = labour.getCommentsList().stream()
                    .map(Comments::toDto).collect(Collectors.toList());
        } else {
            commentsDtoList = new ArrayList<>();
        }
        return new LabourWithCommentsDto(
                labour.getId(),
                labour.getDeadline(),
                commentsDtoList,
                labour.getEmployee().getId(),
                labour.getEmployee().getFullName()

        );
    }

}
