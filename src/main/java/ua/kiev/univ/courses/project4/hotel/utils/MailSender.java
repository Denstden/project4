package ua.kiev.univ.courses.project4.hotel.utils;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import static org.apache.log4j.Logger.getLogger;

/**
 * Class MailSender allows to send email to a specific mail.
 */
public class MailSender {
    public static final Logger logger = getLogger(MailSender.class);
    private String from;
    private String username;
    private String password;

    public void init() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("conf", Locale.getDefault());
        from = resourceBundle.getString("mailFrom");
        username = resourceBundle.getString("username");
        password = resourceBundle.getString("password");
    }

    public void sendMail(String to, String subject, String mssg){
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(mssg);

            // Send message
            Transport.send(message);
        } catch (MessagingException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
