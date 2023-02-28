package com.example.welcometoesprit.ServicesImpl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PDFGeneratorService {
    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        // Add a title
        Paragraph title = new Paragraph("Certificate of Completion");
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add a subtitle
        Paragraph subtitle = new Paragraph("This is to certify that");
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingBefore(20f);
        document.add(subtitle);

        // Add the recipient's name
        Image img = Image.getInstance(new File("C:\\Users\\noura\\Downloads\\qrcode.png").getAbsolutePath());
        img.setAlignment(Element.ALIGN_CENTER);
        img.scaleToFit(100f, 100f);
        document.add(img);
        Paragraph name = new Paragraph("John Doe");
        name.setAlignment(Element.ALIGN_CENTER);
        name.setSpacingBefore(20f);
        document.add(name);

        // Add the course name and date
        PdfPTable table = new PdfPTable(2);
        PdfPCell cell1 = new PdfPCell(new Paragraph("Course Name:"));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Spring Boot Basics"));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Completion Date:"));
        PdfPCell cell4 = new PdfPCell(new Paragraph(new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.setSpacingBefore(20f);
        document.add(table);

        // Add a message
        Paragraph message = new Paragraph("We hereby certify that the above-named person has successfully completed the course and is awarded this certificate.");
        message.setAlignment(Element.ALIGN_JUSTIFIED);
        message.setSpacingBefore(20f);
        document.add(message);

        document.close();
    }


    public void importPDF(){

    }

}