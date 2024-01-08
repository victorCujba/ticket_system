package it.webformat.ticketsystem.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.webformat.ticketsystem.data.archetypes.Dto;
import it.webformat.ticketsystem.data.models.Comments;
import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.data.models.Labour;
import it.webformat.ticketsystem.data.models.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static it.webformat.ticketsystem.utility.DataConversionUtils.stringToLong;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabourDto implements Dto {

    private Long id;
    private String desc;
    private String deadline;
    private List<EmployeeDto> employeeDtoList;
    private String projectId;
    @JsonIgnore
    private List<CommentsDto> commentsDtoList;

    @Override
    public Labour toModel() {

        List<Comments> commentsList;
        if (!(commentsDtoList == null)) {
            commentsList = commentsDtoList.stream()
                    .map(CommentsDto::toModel).toList();
        } else {
            commentsList = new ArrayList<>();
        }

        List<Employee> employeeList;
        if (!(employeeDtoList == null)) {
            employeeList = employeeDtoList.stream()
                    .map(EmployeeDto::toModel).toList();
        } else {
            employeeList = new ArrayList<>();
        }


        return Labour.builder()
                .id(id)
                .description(desc)
                .deadline(LocalDate.parse(deadline))
                .employeeList(employeeList)
                .project(Project.builder().id(stringToLong(projectId)).build())
                .commentsList(commentsList)
                .build();
    }
}
