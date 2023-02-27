package tn.workbot.coco_marketplace.Api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class OrderStatsPDFGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderStatsPDFGenerator.class);
	
	public static ByteArrayInputStream RankGouvernoratByOrdersNumberPDFReport(List<String> stats) {
		Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try {
        	
        	PdfWriter.getInstance(document, out);
            document.open();
        	
			// Add Text to PDF file ->
			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.DARK_GRAY);
			Font small = FontFactory.getFont(FontFactory.COURIER, 9, BaseColor.BLUE);

			Paragraph para = new Paragraph( "Stat Table", font);
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			document.add(Chunk.NEWLINE);
        	PdfPTable table = new PdfPTable(2);
        	// Add PDF Table Header ->
			Stream.of("Stats" , "number")
			    .forEach(headerTitle -> {
			          PdfPCell header = new PdfPCell();
			          Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);;
			          header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			          header.setHorizontalAlignment(Element.ALIGN_CENTER);
			          header.setBorderWidth(2);
			          header.setBorderColor(BaseColor.BLACK);
			          header.setPhrase(new Phrase(headerTitle, headFont));
			          table.addCell(header);
			    });
            
            for (String stat : stats) {
				String charOnly= stat.replaceAll("[^A-z]", "");
				PdfPCell nameCell = new PdfPCell(new Phrase(charOnly));
            	nameCell.setPaddingLeft(4);
            	nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(nameCell);
				String numberOnly= stat.replaceAll("[^0-9]", "");
            	PdfPCell idCell = new PdfPCell(new Phrase(numberOnly));
            	idCell.setPaddingLeft(4);
            	idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idCell);

            }

			Paragraph p = new Paragraph("\n"+new Date(System.currentTimeMillis()),small);
            document.add(table);
			document.add(p);
            document.close();


        }catch(DocumentException e) {
        	logger.error(e.toString());
        }
        
		return new ByteArrayInputStream(out.toByteArray());
	}

	public static ByteArrayInputStream OrderRankForUsersPDFReport(List<String> stats) {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			PdfWriter.getInstance(document, out);
			document.open();

			// Add Text to PDF file ->
			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.DARK_GRAY);
			Font small = FontFactory.getFont(FontFactory.COURIER, 9, BaseColor.BLUE);

			Paragraph para = new Paragraph( "Stat Table", font);
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			document.add(Chunk.NEWLINE);
			PdfPTable table = new PdfPTable(2);
			// Add PDF Table Header ->
			Stream.of("FirstName LastName" , "number")
					.forEach(headerTitle -> {
						PdfPCell header = new PdfPCell();
						Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);;
						header.setBackgroundColor(BaseColor.LIGHT_GRAY);
						header.setHorizontalAlignment(Element.ALIGN_CENTER);
						header.setBorderWidth(2);
						header.setBorderColor(BaseColor.BLACK);
						header.setPhrase(new Phrase(headerTitle, headFont));
						table.addCell(header);
					});

			for (String stat : stats) {
				String charOnly= stat.replaceAll("[^A-z]", "");
				PdfPCell nameCell = new PdfPCell(new Phrase(charOnly));
				nameCell.setPaddingLeft(4);
				nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(nameCell);
				String numberOnly= stat.replaceAll("[^0-9]", "");
				PdfPCell idCell = new PdfPCell(new Phrase(numberOnly));
				idCell.setPaddingLeft(4);
				idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(idCell);

			}

			Paragraph p = new Paragraph("\n"+new Date(System.currentTimeMillis()),small);
			document.add(table);
			document.add(p);
			document.close();


		}catch(DocumentException e) {
			logger.error(e.toString());
		}

		return new ByteArrayInputStream(out.toByteArray());
	}







}