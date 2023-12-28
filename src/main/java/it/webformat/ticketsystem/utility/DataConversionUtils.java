package it.webformat.ticketsystem.utility;

import it.webformat.ticketsystem.enums.EmployeeRole;

import java.time.LocalDateTime;

public class DataConversionUtils {

    private DataConversionUtils() {

    }

    public static String numberToString(Number value) {
        return value == null ? null : value.toString();
    }

    public static Long stringToLong(String value) {
        return value == null ? null : Long.parseLong(value);
    }


    public static LocalDateTime stringToLocalDateTime(String value) {
        return value == null ? null : LocalDateTime.parse(value.substring(0, 19));
    }

    public static String localDateTimeToString(LocalDateTime value) {
        return value == null ? null : value.toString();
    }

    public static Boolean stringToBoolean(String value) {
        return value == null ? null : Boolean.valueOf(value);
    }

    public static String booleanToString(Boolean value) {
        return value == null ? null : value.toString();
    }

    public static EmployeeRole stringToEmployeeRole(String value) {
        for (EmployeeRole priorityValue : EmployeeRole.values()) {
            if (priorityValue.name().equalsIgnoreCase(value))
                return priorityValue;
        }
        return null;
    }

    public static String employeeRoleToString(EmployeeRole value) {
        return value == null ? null : value.name();
    }


//    public static ListLabel stringToListLabel(String value) {
//        for (ListLabel cardStatusValue : ListLabel.values()) {
//            if (cardStatusValue.name().equalsIgnoreCase(value))
//                return cardStatusValue;
//        }
//        return null;
//    }
//
//    public static String listLabelToString(ListLabel value) {
//        return value == null ? null : value.name();
//    }


}
