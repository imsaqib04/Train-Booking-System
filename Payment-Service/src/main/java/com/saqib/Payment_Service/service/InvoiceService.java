package com.saqib.Payment_Service.service;


import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.saqib.Payment_Service.model.Payment;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class InvoiceService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendInvoice(Payment pay) throws Exception {

        /* 1️⃣  Build minimal HTML */
        String html = """
        <html><body style='font-family:sans-serif'>
          <h2>Train Booking – Payment Receipt</h2>
          <p><b>Order ID:</b> %s</p>
          <p><b>Payment ID:</b> %s</p>
          <p><b>Amount:</b> ₹%s %s</p>
          <p><b>Date:</b> %s</p>
          <hr/>
          <p>Thank you for booking with us.</p>
        </body></html>
        """.formatted(
                pay.getOrderId(),
                pay.getPaymentId(),
                pay.getAmount(), pay.getCurrency(),
                pay.getUpdatedAt().format( DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm"))
        );

        /* 2️⃣  Convert HTML → PDF (in-memory) */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(html, null);
        builder.toStream(baos);
        builder.run();

        /* 3️⃣  Send mail with attachment */
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper (msg, true);
        helper.setTo("customer@example.com");        // TODO: dynamic email
        helper.setSubject("Payment Receipt – " + pay.getOrderId());
        helper.setText("Hi, \n\nYour payment was successful. Receipt attached.\n\nThanks.");

        ByteArrayResource pdf = new ByteArrayResource(baos.toByteArray());
        helper.addAttachment("Receipt_"+pay.getOrderId()+".pdf", pdf);

        mailSender.send(msg);
    }
}
