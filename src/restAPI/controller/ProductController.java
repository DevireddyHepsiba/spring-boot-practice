package restAPI.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restAPI.model.Product;
import restAPI.service.ProductService;
import restAPI.reports.CsvExporter;
import restAPI.reports.PdfExporter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // ✅ Create a new Product
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product saved = service.addProduct(product);
        return ResponseEntity.created(URI.create("/api/products/" + saved.getId())).body(saved);
    }

    // ✅ Get all products
    @GetMapping
    public List<Product> all() {
        return service.getAllProducts();
    }

    // ✅ Get a product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        return service.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Update product by ID
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product updated = service.updateProduct(id, product);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Export as CSV
    @GetMapping("/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<Product> products = service.getAllProducts();
        CsvExporter.exportToCSV(products, response);
    }

    // ✅ Export as PDF
    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        List<Product> products = service.getAllProducts();
        PdfExporter.exportToPDF(products, response);
    }
}
