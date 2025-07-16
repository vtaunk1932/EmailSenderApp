package com.emailsender.emailsenderapp.services;

import com.emailsender.emailsenderapp.helper.Messages;
import jakarta.mail.MessagingException;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface EmailService{
    void sendEmail(String to,String subject, String message);
    void sendEmail(String[] to,String subject, String message);
    void sendEmailWithHtml(String to, String subject, String htmlContent) throws MessagingException;
    void sendEmailWithFile(String to, String subject, String message, File file);
    void sendEmailWithFile(String to, String subject, String message, InputStream is);
    List<Messages> getInboxMessages();
}
