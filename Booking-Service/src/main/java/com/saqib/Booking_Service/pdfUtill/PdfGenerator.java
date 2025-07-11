package com.saqib.Booking_Service.pdfUtill;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.saqib.Booking_Service.model.Booking;
import com.saqib.Booking_Service.model.Passenger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class PdfGenerator {

    public static ByteArrayInputStream generateInvoice(Booking booking) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Optional: Add logo (if exists)
            // ImageData logo = ImageDataFactory.create("src/main/resources/logo.png");
            // Image image = new Image(logo).scaleToFit(100, 100).setHorizontalAlignment(HorizontalAlignment.CENTER);
            // document.add(image);

            // Header
            Paragraph header = new Paragraph("Train Booking Invoice")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(18);
            document.add(header);

            // Line
            document.add(new LineSeparator(new SolidLine(1)));

            // Booking Info
            document.add(new Paragraph("Booking ID: " + booking.getBookingId()));
            document.add(new Paragraph("PNR Number: " + booking.getPnrNumber()));
            document.add(new Paragraph("Booking Date: " + booking.getBookingTime()));
            document.add(new Paragraph("Journey Date: " + booking.getJourneyDate()));
            document.add(new Paragraph("Train ID: " + booking.getTrainId()));
            document.add(new Paragraph("Coach Type: " + booking.getCoachType()));
            document.add(new Paragraph("Status: " + booking.getStatus()));

            document.add(new LineSeparator(new SolidLine(1)));

            // Passenger Table
            Paragraph pHeader = new Paragraph("Passenger Details").setBold().setFontSize(14);
            document.add(pHeader);

            Table table = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth();
            table.addHeaderCell("Name");
            table.addHeaderCell("Age");
            table.addHeaderCell("Gender");
            table.addHeaderCell("Seat Pref.");
            table.addHeaderCell("Fare");
            table.addHeaderCell("ID Proof");

//            for (Passenger p : booking.getPassengers()) {
//                table.addCell(p.getName());
//                table.addCell(String.valueOf(p.getAge()));
//                table.addCell(p.getGender().toString());
//                table.addCell((p.getGender()).toString () );
//                table.addCell(p.getSeatPreference().toString ());
//                table.addCell(String.format("%.2f", p.getFare()));
//                table.addCell(p.getIdProofType());

            Paragraph paymentInfo = new Paragraph("Payment Details")
                    .setFont( pdfDoc.getDefaultFont () )
                    .setFontSize(14)
                    .setMarginTop(20);

            Table payTable = new Table(new float[]{200, 300});
            payTable.addCell("Payment ID");
            payTable.addCell(booking.getPaymentId() != null ? booking.getPaymentId() : "N/A");

            payTable.addCell("Order ID");
            payTable.addCell(booking.getPnrNumber());

            payTable.addCell("Payment Status");
            payTable.addCell(booking.getPaymentStatus().toString());

            payTable.addCell("Payment Date");
            payTable.addCell(booking.getBookingTime().toString());


            for (Passenger p : booking.getPassengers()) {
                table.addCell( Objects.toString(p.getName(), "") );
                table.addCell( String.valueOf(p.getAge()) );

                /* ——‑‑ null‑safe cells ‑‑—— */
                table.addCell( Objects.toString(p.getGender(), "") );          // gender
                table.addCell( Objects.toString(p.getSeatPreference(), "") );  // seat pref.
                table.addCell( String.format("%.2f", p.getFare()) );
                table.addCell( Objects.toString(p.getIdProofType(), "") );     // id proof
            }

            document.add(table);
            document.add(paymentInfo);
            document.add(payTable);


//          document.add(new LineSeparator(new SolidLine(1f)));
            // Create a solid line (this implements ILineDrawer)
            SolidLine line = new SolidLine(1f);
            LineSeparator separator = new LineSeparator(line);
            document.add(separator);

            // Footer
            document.add(new Paragraph("Total Fare: ₹" + booking.getTotalFare()).setBold());
            document.add(new Paragraph("Thank you for booking with us!").setTextAlignment(TextAlignment.CENTER));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
