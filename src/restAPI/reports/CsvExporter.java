package restAPI.reports;

import restAPI.model.Product;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CsvExporter {

    public static void exportToCSV(List<Product> products, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=products.csv";
        response.setHeader(headerKey, headerValue);

        PrintWriter writer = response.getWriter();
        writer.println("ID,Name,Price");

        for (Product product : products) {
            writer.println(product.getId() + "," + product.getName() + "," + product.getPrice());
        }

        writer.flush();
        writer.close();
    }
}
