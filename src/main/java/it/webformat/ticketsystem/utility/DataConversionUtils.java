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

    public static String employeeRoleToString(EmployeeRole value) {
        return value == null ? null : value.name();
    }


}
