package tn.workbot.coco_marketplace.Api;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Pickup;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.Store;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.PickupRepository;
import tn.workbot.coco_marketplace.repositories.ProductRepository;
import tn.workbot.coco_marketplace.repositories.StoreRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.PickupService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class PdfPickup {
    @Autowired
    PickupRepository pr;
    @Autowired
    ProductRepository ppr;
    @Autowired
    UserrRepository ur;
    @Autowired
    PickupService ps;
    @Autowired
    StoreRepository sr;


    public void pdfPickup(HttpServletResponse response,Long idPickup) throws IOException {
        Pickup pickup=pr.findById(idPickup).get();
        List<Product> productList=ppr.findAll();
        List<Product> s=pr.ProductBystorebyPickup(idPickup);
        List<Product> products=new ArrayList<>();
        //session manager idSeller
        User u=ur.findById(1L).get();
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        // Set font and font size
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
        // Add title
        contentStream.beginText();
        contentStream.newLineAtOffset(70, 760);
        contentStream.showText("Pickup Details");
        contentStream.endText();
        // Add pickup date
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(70, 710);
        contentStream.showText("Date: " + pickup.getDateCreationPickup());
        contentStream.endText();
        // Add pickup address
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(70, 690);
        contentStream.showText("Address: " + pickup.getGovernorate() +"/" +pickup.getCity());
        contentStream.endText();
        // Add pickup NomBuyer
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(70, 650);
        contentStream.showText("FirstName: " + pickup.getOrder().getBuyer().getFirstName()+ "   LastName: "+ pickup.getOrder().getBuyer().getLastName()+"  Phone Number: "+ pickup.getOrder().getBuyer().getPhoneNumber());
        contentStream.endText();
        // Add pickup NomStore
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(70, 670);
        contentStream.showText("Name Of Store: " + pickup.getStore().getName());
        contentStream.endText();
        List<Product> ps=pr.ProductBystorebyPickup(idPickup);
        float price=0;
        for (Product p:ps) {
            price=p.getProductPrice()+price;
        }
        // Add pickup NomStore
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 15);
        contentStream.newLineAtOffset(70, 630);
        contentStream.showText("Total: " +price+"DT ");
        contentStream.endText();

        // Add table of items
        float tableTopY = 600;
        drawTableHeader(contentStream, tableTopY);
        drawTableRows(contentStream, tableTopY - 20, products,idPickup);

        contentStream.close();
        // Add total to footer

        contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(50, 50);
        contentStream.showText("Total: " +price);
        contentStream.endText();
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
        float[] columnWidths = {50, 100, 200};

        // Define table header row
        String[] headers = {"", "Description", "Price"};

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





    private void drawTableRows(PDPageContentStream contentStream, float y, List<Product> items, Long idPickup) throws IOException {
        // Define column widths
        float[] columnWidths = {100, 150, 280};
        // Set font and font size for table rows
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        List<Product> s=pr.ProductBystorebyPickup(idPickup);
        System.out.println("test");
        System.out.println(s);
        // Draw table rows
        float tableTopY = y;
            for (Product item : s) {
            contentStream.beginText();
            contentStream.newLineAtOffset(70, tableTopY);
            contentStream.showText("");
            contentStream.newLineAtOffset(columnWidths[0], -5);
            contentStream.showText(item.getDescription());
            contentStream.newLineAtOffset(columnWidths[2], -5);
            contentStream.showText(String.valueOf(item.getProductPrice()));
            contentStream.endText();

            // Draw table row bottom line
            contentStream.moveTo(50, tableTopY - 15);
            contentStream.lineTo(500, tableTopY - 15);
            contentStream.stroke();

            tableTopY -= 20;
        }

    }




}
