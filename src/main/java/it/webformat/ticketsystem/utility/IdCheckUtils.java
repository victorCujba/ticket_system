package it.webformat.ticketsystem.utility;

import it.webformat.ticketsystem.data.models.*;

import static it.webformat.ticketsystem.utility.DataConversionUtils.numberToString;

public class IdCheckUtils {

    public static String getIdOrNull(Labour labour) {
        return (labour != null && labour.getId() != null) ? numberToString(labour.getId()) : null;
    }

    public static String getIdOrNull(Project project) {
        return (project != null && project.getId() != null) ? numberToString(project.getId()) : null;
    }

    public static String getIdOrNull(Badge badge) {
        return (badge != null && badge.getId() != null) ? numberToString(badge.getId()) : null;
    }

    public static String getIdOrNull(Team team) {
        return (team != null && team.getId() != null) ? numberToString(team.getId()) : null;
    }

    public static String getIdOrNull(Employee employee) {
        return (employee != null && employee.getId() != null) ? numberToString(employee.getId()) : null;
    }


}
