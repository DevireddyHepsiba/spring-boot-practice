package restAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restAPI.model.Product;
import restAPI.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;

    public Product addProduct(Product product) {
        return repo.save(product);
    }

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return repo.findById(id);
    }

    public Product updateProduct(Long id, Product product) {
        return repo.findById(id).map(p -> {
            p.setName(product.getName());
            p.setPrice(product.getPrice());
            return repo.save(p);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(Long id) {
        repo.deleteById(id);
    }
}
