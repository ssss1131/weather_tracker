package com.ssss.weather_tracker.util;

import lombok.experimental.UtilityClass;
import org.mindrot.jbcrypt.BCrypt;

@UtilityClass
public class PasswordSecurity {

    public static String hash(String pass){
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }

    public static boolean isEqualPasswords(String rawPass, String hashedPass){
        return BCrypt.checkpw(rawPass, hashedPass);
    }

}
