package vn.iotstar.config;

import java.io.InputStream;
import java.util.Properties;

public class Constants {
    private static final Properties P = new Properties();
    static {
        try (InputStream in = Constants.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) P.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static final String DIR = P.getProperty("upload.dir", System.getProperty("user.home") + "/upload");
    public static final String MAIL_USER = P.getProperty("mail.user", "");
    public static final String MAIL_PASSWORD = P.getProperty("mail.password", "");
}
