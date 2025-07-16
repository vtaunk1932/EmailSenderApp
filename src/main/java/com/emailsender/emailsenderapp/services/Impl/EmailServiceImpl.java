package com.emailsender.emailsenderapp.services.Impl;

import com.emailsender.emailsenderapp.helper.Messages;
import com.emailsender.emailsenderapp.services.EmailService;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("vasutaunk1932@gmail.com");
        mailSender.send(simpleMailMessage);
        logger.info("Email sent successfully to {}", to);
    }

    @Override
    @Async
    public void sendEmail(String[] to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("vasutaunk1932@gmail.com");
        mailSender.send(simpleMailMessage);
        logger.info("Email sent successfully to multiple recipients");
    }

    @Override
    @Async
    public void sendEmailWithHtml(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom("vasutaunk1932@gmail.com");
        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
        logger.info("HTML email sent successfully to {}", to);
    }

    @Override
    @Async
    public void sendEmailWithFile(String to, String subject, String message, File file) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("vasutaunk1932@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message);
            FileSystemResource fileSystemResource = new FileSystemResource(file);
            helper.addAttachment(fileSystemResource.getFilename(), file);
            mailSender.send(mimeMessage);
            logger.info("Email with file attachment sent successfully to {}", to);
        } catch (MessagingException e) {
            logger.error("Failed to send email with file attachment", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Async
    public void sendEmailWithFile(String to, String subject, String message, InputStream is) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("vasutaunk1932@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message);

            File tempFile = File.createTempFile("attachment", ".tmp");
            Files.copy(is, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            FileSystemResource fileSystemResource = new FileSystemResource(tempFile);
            helper.addAttachment(fileSystemResource.getFilename(), tempFile);

            mailSender.send(mimeMessage);
            logger.info("Email with file stream sent successfully to {}", to);
        } catch (MessagingException | IOException e) {
            logger.error("Failed to send email with file stream", e);
            throw new RuntimeException(e);
        }
    }

    @Value("${mail.store.protocol}")
    private String protocol;

    @Value("${mail.imaps.host}")
    private String host;

    @Value("${mail.imaps.port}")
    private String port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Override
    public List<Messages> getInboxMessages() {
        List<Messages> messagesList = new ArrayList<>();
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", protocol);
        properties.setProperty("mail.imaps.host", host);
        properties.setProperty("mail.imaps.port", port);

        try {
            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore();
            store.connect(username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            int messageCount = inbox.getMessageCount();
            int start = Math.max(1, messageCount - 10); // Fetch last 10 emails
            int end = messageCount;

            jakarta.mail.Message[] messages = inbox.getMessages(start, end);
            for (jakarta.mail.Message message : messages) {
                String content = getContentFromEmailMessage(message);
                List<String> files = getFilesFromEmailMessage(message);
                messagesList.add(Messages.builder()
                        .subjects(message.getSubject())
                        .content(content)
                        .files(files)
                        .build());
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            logger.error("Failed to fetch inbox messages", e);
            throw new RuntimeException(e);
        }

        return messagesList;
    }

    private List<String> getFilesFromEmailMessage(jakarta.mail.Message message) throws MessagingException, IOException {
        List<String> files = new ArrayList<>();
        if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                    File file = File.createTempFile("attachment", bodyPart.getFileName());
                    Files.copy(bodyPart.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    files.add(file.getAbsolutePath());
                }
            }
        }
        return files;
    }

    private String getContentFromEmailMessage(jakarta.mail.Message message) throws MessagingException, IOException {
        if (message.isMimeType("text/plain") || message.isMimeType("text/html")) {
            return (String) message.getContent();
        } else if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    return (String) bodyPart.getContent();
                }
            }
        }
        return null;
    }
}