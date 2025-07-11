//package com.saqib.Payment_Service.service;
//
//import com.lowagie.text.*;
//import com.lowagie.text.pdf.*;
//import com.lowagie.text.FontFactory;
//import com.saqib.Payment_Service.dto.*;
//import com.saqib.Payment_Service.feign.BookingClient;
//import com.saqib.Payment_Service.feign.TrainClient;
//import com.saqib.Payment_Service.feign.UserClient;
//import com.saqib.Payment_Service.model.Payment;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.InputStreamSource;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayOutputStream;
//import java.time.format.DateTimeFormatter;
//import java.util.Objects;
//
//@Service
//public class InvoiceService {
//
//    @Autowired private JavaMailSender mailSender;
//    @Autowired private BookingClient bookingClient;
//    @Autowired private TrainClient   trainClient;
//    @Autowired private UserClient    userClient;
//
//    /* ---------------------------------------------------- */
//    public void sendInvoice(Payment pay) throws Exception {
//
//        BookingDto booking = retrieveBooking(pay);
//        UserDto    user    = userClient.getUserById(booking.getUserId());
//        TrainDto   train   = trainClient.getByTrainId (booking.getTrainId ());
//
//        if (user.getEmail() == null || user.getEmail().isBlank()) {
//            System.err.println("⚠️  No e‑mail for user " + user.getUserId() + "; invoice skipped.");
//            return;
//        }
//
//        byte[] pdf = buildTicketPdf(pay, booking, train, user);
//
//        sendMailWithAttachment(
//                user.getEmail(),
//                "Your Train Ticket – PNR " + booking.getPnrNumber(),
//                "Dear " + user.getFullName()
//                        + ",\n\nYour payment was successful. "
//                        + "Please find your e‑ticket attached.\n\nHappy Journey!",
//                pdf);
//    }
//
//    private BookingDto retrieveBooking(Payment pay) {
//        return pay.getBookingId() != null
//                ? bookingClient.getBookingById(pay.getBookingId())
//                : bookingClient.getBookingByPnr(pay.getPnrNumber());
//    }
//
//    /* -------------------- PDF Builder -------------------- */
//    private byte[] buildTicketPdf(Payment pay,
//                                  BookingDto booking,
//                                  TrainDto   train,
//                                  UserDto    user) throws Exception {
//
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        Document doc = new Document();
//        PdfWriter.getInstance(doc, bos);
//        doc.open();
//
//        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
//        Paragraph title = new Paragraph("E‑Ticket", titleFont);
//        title.setAlignment(Element.ALIGN_CENTER);
//        doc.add(title);
//        doc.add(new Paragraph(" "));
//
//        PdfPTable table = new PdfPTable(2);
//        table.setWidthPercentage(100);
//
//        addRow(table, "Passenger",      nvl(user.getFullName()));
//        addRow(table, "PNR",            nvl(booking.getPnrNumber()));
//        addRow(table, "Train",          nvl(train.getTrainId ()) + " – " + nvl(train.getTrainName()));
//        addRow(table, "Journey Date",   booking.getJourneyDate() != null
//                ? booking.getJourneyDate().format(DateTimeFormatter.ISO_DATE)
//                : "–");
//        addRow(table, "From",           nvl(train.getSourceStation()));
//        addRow(table, "To",             nvl(train.getDestinationStation()));
//        addRow(table, "Seats",          String.valueOf(booking.getSeatCount()));
//        addRow(table, "Total Fare",     pay.getAmount() + " " + pay.getCurrency());
//        addRow(table, "Payment ID",     nvl(pay.getPaymentId()));
//        addRow(table, "Status",         pay.getStatus().name());
//
//        doc.add(table);
//        doc.close();
//        return bos.toByteArray();
//    }
//
//    private void addRow(PdfPTable t, String k, String v) {
//        PdfPCell c1 = new PdfPCell(new Phrase(k));
//        PdfPCell c2 = new PdfPCell(new Phrase(v));
//        c1.setBorderWidth(0); c2.setBorderWidth(0);
//        t.addCell(c1); t.addCell(c2);
//    }
//
//    private void sendMailWithAttachment(String to, String subj, String body, byte[] attachment)
//            throws MessagingException {
//
//        MimeMessage msg = mailSender.createMimeMessage();
//        MimeMessageHelper h = new MimeMessageHelper(msg, true);
//
//        h.setTo(to);
//        h.setSubject(subj);
//        h.setText(body);
//
//        InputStreamSource src = new ByteArrayResource(attachment);
//        h.addAttachment("ticket.pdf", src);
//
//        mailSender.send(msg);
//    }
//
//    private static String nvl(Object o) { return Objects.toString(o, "–"); }
//}
