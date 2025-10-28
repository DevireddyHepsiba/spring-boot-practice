package restAPI.reports;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import restAPI.model.Product;
import java.awt.Color;
import java.io.IOException;
import java.util.List;

public class PdfExporter {

    public static void exportToPDF(List<Product> products, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=products.pdf";
        response.setHeader(headerKey, headerValue);

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        fontTitle.setColor(Color.BLUE);

        Paragraph title = new Paragraph("Product Report", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" ")); // Empty line

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 2.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table, products);

        document.add(table);
        document.close();
    }

    private static void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Price", font));
        table.addCell(cell);
    }

    private static void writeTableData(PdfPTable table, List<Product> listProducts) {
        for (Product product : listProducts) {
            table.addCell(String.valueOf(product.getId()));
            table.addCell(product.getName());
            table.addCell(String.valueOf(product.getPrice()));
        }
    }
}
