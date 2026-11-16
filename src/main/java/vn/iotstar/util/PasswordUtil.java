package vn.iotstar.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String hash(String raw) {
        return BCrypt.hashpw(raw, BCrypt.gensalt());
    }

    public static boolean check(String raw, String hashed) {
        try {
            return raw != null && hashed != null && BCrypt.checkpw(raw, hashed);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
