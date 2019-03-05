package vecirky.exports;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;

import vecirky.models.Event;

/**
 * Using methods from class ExportByEvent to generate a .pdf file.
 */

public class ExportToFile {

    public void export(Event event, String path){
        Document document = new Document();
        ExportByEvent export = new ExportByEvent();
        Font bold = new Font(FontFamily.HELVETICA, 16, Font.BOLD);


        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            Paragraph paragraph3 = new Paragraph(export.eventType(event), bold);
            paragraph3.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph3);
            Paragraph paragraph4 = new Paragraph(export.client(event));
            document.add(paragraph4);
            Paragraph paragraph5 = new Paragraph(export.mainPromoter(event));
            document.add(paragraph5);
            Paragraph paragraph2 = new Paragraph(export.eventDate(event));
            document.add(paragraph2);
            Paragraph paragraph1 = new Paragraph(export.address(event));
            document.add(paragraph1);
            Paragraph paragraph = new Paragraph(export.description(event));
            document.add(paragraph);
            Paragraph paragraph6 = new Paragraph(export.price(event));
            document.add(paragraph6);
            document.addCreationDate();
            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}