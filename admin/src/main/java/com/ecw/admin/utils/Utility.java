package com.ecw.admin.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utility {

  /*  public static Long getUserId(SecurityContext sc){
        MyUserDetails user=(MyUserDetails) sc.getAuthentication().getPrincipal();
        return user.getUserid();
    }
    public static String getUserName(SecurityContext sc){
        MyUserDetails user=(MyUserDetails) sc.getAuthentication().getPrincipal();
        return user.getUsername();

    }*/
    public static boolean isDatePast(final String date, final String dateFormat) {
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate inputDate = LocalDate.parse(date, dtf);

        return inputDate.isBefore(localDate);
    }
    public static boolean isDigit(String str){
        try{
            Integer.parseInt(str);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
