package com.example.javamail;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import java.util.Properties;

public class JavaMailService implements CommandLineRunner {
    // These are the properties that will be injected from application.properties
    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.port}")
    private String port;

    @Value("${mail.smtp.auth}")
    private String auth;

    @Value("${mail.smtp.socketFactory.port}")
    private String socketFactoryPort;

    @Value("${mail.smtp.socketFactory.class}")
    private String socketFactoryClass;

    @Value("${mail.recipient.to}")
    private String recipientTo;

    @Value("${mail.subject}")
    private String subject;

    @Value("${mail.text}")
    private String text;

    public static void main(String[] args) {
        SpringApplication.run(JavaMailService.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        sendEmail();
    }

    public void sendEmail() {
        // Set up the properties for the SMTP connection
        Properties prop = new Properties();
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.auth", auth);
        prop.put("mail.smtp.socketFactory.port", socketFactoryPort);
        prop.put("mail.smtp.socketFactory.class", socketFactoryClass);

        // Create a session with the properties and an authenticator
        Session session = Session.getInstance(prop,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientTo)
            );
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}