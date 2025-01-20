package com.expense.tracker.auth.Util;

import java.util.regex.Pattern;

public class UserUtility {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
    public static boolean validatePassword(String password){
        if(password==null){
            return false;
        }
        else {
            return pattern.matcher(password).matches();//it will match with the pattern
        }
    }

//    Explanation:
//
//    The regular expression ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$ checks the following:
//
//            (?=.*[0-9]): At least one numeric digit.
//
//            (?=.*[a-z]): At least one lowercase letter.
//
//            (?=.*[A-Z]): At least one uppercase letter.
//
//            (?=.*[@#$%^&+=]): At least one special character from the set @#$%^&+=.
//
//            (?=\\S+$): No whitespace characters (spaces, tabs, etc.) allowed.
//
//            .{8,}$: Minimum length of 8 characters.


}
