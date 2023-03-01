package tn.workbot.coco_marketplace.Api;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Pickup;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.repositories.PickupRepository;
import tn.workbot.coco_marketplace.repositories.ProductRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfPickup {
    @Autowired
    PickupRepository pr;
    @Autowired
    ProductRepository ppr;

    public void pdfPickup(HttpServletResponse response,Long idPickup) throws IOException {
        Pickup pickup=pr.findById(idPickup).get();
        List<Product> products=ppr.findAll();
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Set font and font size
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);

        // Add title
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 750);
        contentStream.showText("Pickup Details");
        contentStream.endText();

        // Add pickup date
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(50, 700);
        contentStream.showText("Date: " + pickup.getDateCreationPickup());
        contentStream.endText();

        // Add pickup address
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(50, 680);
        contentStream.showText("Address: " + pickup.getGovernorate() +pickup.getCity());
        contentStream.endText();

        // Add table of items
        float tableTopY = 600;
        drawTableHeader(contentStream, tableTopY);
        drawTableRows(contentStream, tableTopY - 20, products);

        contentStream.close();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();
        response.setHeader("Content-Disposition", "attachment; filename=Pickup.pdf");
        response.setContentType("application/pdf");
        response.getOutputStream().write(baos.toByteArray());
        response.getOutputStream().flush();
    }


    private void drawTableHeader(PDPageContentStream contentStream, float y) throws IOException {
        // Define column widths
        float[] columnWidths = {100, 300, 100, 100};

        // Define table header row
        String[] headers = {"Item ID", "Description", "Quantity", "Weight"};

        // Set font and font size for table header
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

        // Draw table header row
        float tableTopY = y;
        for (int i = 0; i < headers.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(50 + i * columnWidths[i], tableTopY);
            contentStream.showText(headers[i]);
            contentStream.endText();
        }

        // Draw table header bottom line
        contentStream.moveTo(50, tableTopY - 15);
        contentStream.lineTo(500, tableTopY - 15);
        contentStream.stroke();
    }





    private void drawTableRows(PDPageContentStream contentStream, float y, List<Product> items) throws IOException {
        // Define column widths
        float[] columnWidths = {100, 300, 100, 100};

        // Set font and font size for table rows
        contentStream.setFont(PDType1Font.HELVETICA, 10);

        // Draw table rows
        float tableTopY = y;
            for (Product item : items) {
            contentStream.beginText();
            contentStream.newLineAtOffset(50, tableTopY);
            contentStream.showText("ddd");
            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(item.getDescription());
            contentStream.newLineAtOffset(columnWidths[1], 0);
            contentStream.showText(String.valueOf("dd"));
            contentStream.newLineAtOffset(columnWidths[2], 0);
            contentStream.showText(String.valueOf("eee"));
            contentStream.endText();

            // Draw table row bottom line
            contentStream.moveTo(50, tableTopY - 15);
            contentStream.lineTo(500, tableTopY - 15);
            contentStream.stroke();

            tableTopY -= 20;
        }
    }




}
