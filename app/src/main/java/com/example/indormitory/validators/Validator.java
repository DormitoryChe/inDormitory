package com.example.indormitory.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vproh on 13.03.2018.
 */

 public class Validator {
    private static final String EMAIL_REGEX = "^\\w+([\\.-]?\\w+){1,3}@(\\w+\\.\\w{2,3})$";
    private static final String NAME_REGEX = "^\\w+([\\.-]?\\w+){1,3}@(\\w+\\.\\w{2,3})$";
    private static final String PHONE_NUMBER_REGEX = "^\\w+([\\.-]?\\w+){1,3}@(\\w+\\.\\w{2,3})$";
    private static final String PASSWORD_REGEX = "^\\w+([\\.-]?\\w+){1,3}@(\\w+\\.\\w{2,3})$";
    private static final int MIN_LOGIN_INPUT_CHARS = 5;
    private static final int MIN_PASSWORD_INPUT_CHARS = 6;

    public static boolean isEmailValid(String email) {
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

        return emailPattern.matcher(email).matches();
    }

    public static  boolean isEmailFieldEmpty (int emailLen) {
        return emailLen == 0;
    }
    public static boolean isEmailLenghtCorrect (int emailLen) {
        return !(emailLen < MIN_LOGIN_INPUT_CHARS);
    }

    public static boolean isPasswordLengthCorrect (int passwordLen) {
        return !(passwordLen < MIN_PASSWORD_INPUT_CHARS);
    }

    public  static boolean isPasswordFieldEmpty (int passwordLen) {
        return passwordLen == 0;
    }

}
