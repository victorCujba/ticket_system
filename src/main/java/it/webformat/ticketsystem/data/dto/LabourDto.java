package it.webformat.ticketsystem.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.webformat.ticketsystem.data.archetypes.Dto;
import it.webformat.ticketsystem.data.models.Comments;
import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.data.models.Labour;
import it.webformat.ticketsystem.data.models.Project;
import it.webformat.ticketsystem.enums.TaskStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static it.webformat.ticketsystem.utility.DataConversionUtils.stringToLong;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabourDto implements Dto {

    private Long id;
    private String desc;
    private String deadline;
    private TaskStatus taskStatus;
    private String employeeId;
    private String projectId;
    @JsonIgnore
    private List<CommentsDto> commentsDtoList;

    @Override
    public Labour toModel() {

        List<Comments> commentsList;
        if (!(commentsDtoList == null)) {
            commentsList = commentsDtoList.stream()
                    .map(CommentsDto::toModel)
                    .collect(Collectors.toList());
        } else {
            commentsList = new ArrayList<>();
        }


        return Labour.builder()
                .id(id)
                .description(desc)
                .deadline(LocalDate.parse(deadline))
                .employee(Employee.builder().id(stringToLong(employeeId)).build())
                .project(Project.builder().id(stringToLong(projectId)).build())
                .commentsList(commentsList)
                .taskStatus(taskStatus)
                .build();
    }
}
