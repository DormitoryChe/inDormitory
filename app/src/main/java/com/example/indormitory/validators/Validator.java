package com.example.indormitory.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vproh on 13.03.2018.
 */

 public class Validator {
    private static final String EMAIL_REGEX = "^\\w+([\\.-]?\\w+){1,3}@(\\w+\\.\\w{2,3})$";
    private static final String NAME_REGEX = "^[A-Z][a-z]{1,14}([- ][a-zA-Z]{1,15}){0,3}$";
    private static final String PHONE_NUMBER_REGEX = "^(\\+?38)?(((099)|(098)|(097)|(096)|(095)|(068)|(067)|(066)|(065))\\d{7})$";
    private static final int MIN_PASSWORD_INPUT_CHARS = 6;

    public static boolean isEmailValid(String email) {
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

        return emailPattern.matcher(email).matches();
    }

    public static  boolean isFieldEmpty (String fieldLength) {
        return fieldLength.length() == 0;
    }

    public static boolean isPasswordLengthCorrect (String password) {
        return !(password.length() < MIN_PASSWORD_INPUT_CHARS);
    }

    public static boolean isRegisterNameCorrect (String name) {
        Pattern namePattern = Pattern.compile(NAME_REGEX);

        return namePattern.matcher(name).matches();
    }

    public static boolean isRegisterPhoneCorrect (String phoneNumber) {
        Pattern phonePattern = Pattern.compile(PHONE_NUMBER_REGEX);

        return phonePattern.matcher(phoneNumber).matches();
    }

    public static boolean isPasswordsEquals (String passwordOne, String passwordTwo) {
        return passwordOne.equals(passwordTwo);
    }

}
