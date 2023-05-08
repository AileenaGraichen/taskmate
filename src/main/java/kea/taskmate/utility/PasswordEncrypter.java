package kea.taskmate.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncrypter {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public static String encryptPassword(String passwordToEncrypt){
        return encoder.encode(passwordToEncrypt);
    }

    public static boolean validatePassword(String password, String DBPassword){
        return encoder.matches(password, DBPassword);
    }
}
