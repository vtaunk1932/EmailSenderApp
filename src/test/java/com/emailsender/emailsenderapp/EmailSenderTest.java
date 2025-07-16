package com.emailsender.emailsenderapp;

import com.emailsender.emailsenderapp.helper.Messages;
import com.emailsender.emailsenderapp.services.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@SpringBootTest
public class EmailSenderTest {

    @Autowired
    private EmailService emailService;

    @Test
    void emailSendTest() {
        System.out.println("Sending email...");
        emailService.sendEmail("vasutaunk1932@gmail.com", "Test Email", "This is a test email sent using Spring Boot.");
    }

    @Test
    void sendHtmlInEmail() throws MessagingException {
        String html = "<h1 style='color:red;border:1px solid red;'>Welcome to Learn Code With Vasu</h1>";
        emailService.sendEmailWithHtml("vasutaunk193204@gmail.com", "HTML Email", html);
    }

    @Test
    void sendEmailWithFile() {
        File file = new File("C:\\Users\\ASUS\\Downloads\\me.jpg"); // Corrected path
        emailService.sendEmailWithFile("vasutaunk193204@gmail.com", "Email with File", "This email contains a file attachment.", file);
    }

    @Test
    void sendEmailWithFileStream() throws FileNotFoundException {
        File file = new File("C:\\Users\\ASUS\\Downloads\\me.jpg");
        InputStream is = new FileInputStream(file);
        emailService.sendEmailWithFile("vasutaunk193204@gmail.com", "Email with File Stream", "This email contains a file attachment.", is);
    }

    @Test
    void getInbox() {
        List<Messages> inboxMessages = emailService.getInboxMessages();
        inboxMessages.forEach(item -> {
            System.out.println("Subject: " + item.getSubjects());
            System.out.println("Content: " + item.getContent());
            System.out.println("Files: " + item.getFiles());
            System.out.println("________");
        });
    }
}