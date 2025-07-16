package com.emailsender.emailsenderapp.controller.api;

import com.emailsender.emailsenderapp.helper.CustomResponse;
import com.emailsender.emailsenderapp.services.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/email")
public class EmailController {

    private EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request) throws MessagingException {
        emailService.sendEmailWithHtml(request.getTo()
                , request.getSubject(), request.getMessage());
        return ResponseEntity.ok(
                CustomResponse.builder().message("Email sent successfully").httpStatus(HttpStatus.OK).success(true).build()
        );
    }

    @PostMapping("/send-with-file")
    public ResponseEntity<CustomResponse> sendWithFile(@RequestPart EmailRequest request, @RequestPart MultipartFile file) throws IOException {
        emailService.sendEmailWithFile(request.getTo(), request.getSubject(), request.getMessage(), file.getInputStream());
        return ResponseEntity.ok(
                CustomResponse.builder().message("Email sent successfully").httpStatus(HttpStatus.OK).success(true).build()
        );
    }
}
