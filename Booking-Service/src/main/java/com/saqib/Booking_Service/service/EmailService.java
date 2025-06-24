package com.saqib.Booking_Service.service;

import com.saqib.Booking_Service.model.Booking;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailService {
    @Autowired

    private JavaMailSender mailSender;

    public void sendBookingMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage ();
        message.setFrom ( "your_gmail@gmail.com" );
        message.setTo ( to );
        message.setSubject ( subject );
        message.setText ( body );
        mailSender.send ( message );
    }

    public void sendBookingMailWithAttachment(String to, String subject, String body, byte[] pdfBytes) {
        try {
            MimeMessage message = mailSender.createMimeMessage ();
            MimeMessageHelper helper = new MimeMessageHelper ( message, true ); // true for multipart

            helper.setTo ( to );
            helper.setSubject ( subject );
            helper.setText ( body );

            // PDF attach karna
            helper.addAttachment ( "Ticket-" + System.currentTimeMillis () + ".pdf", new ByteArrayResource ( pdfBytes ) );

            mailSender.send ( message );
            System.out.println ( "✅ Email sent with ticket attachment." );
        } catch (MessagingException e) {
            throw new RuntimeException ( "❌ Failed to send email with PDF attachment", e );
        }
    }
}