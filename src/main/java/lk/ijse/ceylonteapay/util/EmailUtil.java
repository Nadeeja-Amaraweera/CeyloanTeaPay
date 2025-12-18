package lk.ijse.ceylonteapay.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {

    // Use your Gmail and the 16-character App Password
    private static final String FROM_EMAIL = "nadeejaamaraweera@gmail.com";   // your Gmail
    private static final String APP_PASSWORD = "dyejmytvqfikgpsy";    // your App Password

    public static void sendLoginFailEmail(String recipientEmail,
                                          String usernameAttempt,
                                          String passwordAttempt) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail)
            );

            message.setSubject("⚠ Login Failed - Ceylon Tea Pay");

            String body =
                            "Username entered: " + usernameAttempt + "\n" +
                            "Password entered: " + passwordAttempt + "\n" +
                            "Time: " + java.time.LocalDateTime.now();

            message.setText(body);

            Transport.send(message);
            System.out.println("✅ Gmail email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendLoginForgetEmail(String recipientEmail,
                                          String usernameAttempt,
                                          String passwordAttempt) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail)
            );

            message.setSubject("⚠ Forgot UserName or Password - Ceylon Tea Pay");

            // Only include the two strings
            String body = "Correct User Name and Password :\n\n"+"User Name : "+usernameAttempt + "\nPassword : " + passwordAttempt;

            message.setText(body);

            Transport.send(message);
            System.out.println("✅ Gmail email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
