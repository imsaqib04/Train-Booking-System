package com.saqib.Booking_Service.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    public void sendBookingMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your_gmail@gmail.com"); // ✅ Replace with actual sender email
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        try {
            mailSender.send(message);
            log.info("✅ Simple email sent to {}", to);
        } catch (Exception e) {
            log.error("❌ Failed to send simple email to {}: {}", to, e.getMessage());
        }
    }

    @Async
    public void sendBookingMailWithAttachment(String to, String subject, String body, byte[] pdfBytes) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("your_gmail@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            helper.addAttachment("Ticket-" + System.currentTimeMillis() + ".pdf", new ByteArrayResource(pdfBytes));

            mailSender.send(message);
            log.info("✅ Email with PDF sent to {}", to);
        } catch (MessagingException e) {
            log.error("❌ Failed to send email with PDF to {}: {}", to, e.getMessage());
            throw new RuntimeException("❌ PDF email failed", e);
        }
    }
}
