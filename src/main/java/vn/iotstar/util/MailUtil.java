package vn.iotstar.util;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import vn.iotstar.config.Constants;

public class MailUtil {
    public static void send(String to, String subject, String body) throws Exception {
        Properties p = new Properties();
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.port", "587");
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(p, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constants.MAIL_USER, Constants.MAIL_PASSWORD);
            }
        });

        MimeMessage m = new MimeMessage(session);
        m.setFrom(new InternetAddress(Constants.MAIL_USER));
        m.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        m.setSubject(subject, "UTF-8");
        m.setText(body, "UTF-8");
        Transport.send(m);
    }
}
