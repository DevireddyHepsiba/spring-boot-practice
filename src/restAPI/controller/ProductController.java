package restAPI.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restAPI.model.Product;
import restAPI.service.ProductService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product saved = service.addProduct(product);
        return ResponseEntity.created(URI.create("/api/products/" + saved.getId())).body(saved);
    }

    @GetMapping
    public List<Product> all() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        return service.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product updated = service.updateProduct(id, product);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
