package utils;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import static org.apache.log4j.Logger.getLogger;

public class MailSender {
    public static final Logger logger = getLogger(MailSender.class);
    private String from;
    private String username;
    private String password;

    public void init() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("C:\\Java\\hotel\\src\\main\\resources\\conf.properties"));
        } catch (IOException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        from = properties.getProperty("mailFrom");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    public void sendMail(String to, String subject, String mssg){
        // Assuming you are sending email through relay.jangosmtp.net
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
