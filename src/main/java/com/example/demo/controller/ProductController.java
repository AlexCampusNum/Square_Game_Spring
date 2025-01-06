package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Map<String, Object> getAllProducts() {
        List<Map<String, Object>> products = productService.getAllProducts().stream()
                .map(product -> Map.of(
                        "type", "products",
                        "id", product.getId().toString(),
                        "attributes", Map.of(
                                "name", product.getName(),
                                "price", product.getPrice()
                        )
                ))
                .collect(Collectors.toList());
        return Map.of("data", products);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return Map.of(
                "data", Map.of(
                        "type", "products",
                        "id", product.getId().toString(),
                        "attributes", Map.of(
                                "name", product.getName(),
                                "price", product.getPrice()
                        )
                )
        );
    }

    @PostMapping
    public Map<String, Object> createProduct(@RequestBody Map<String, Object> request) {
        Map<String, Object> data = (Map<String, Object>) request.get("data");
        Map<String, Object> attributes = (Map<String, Object>) data.get("attributes");
        Product product = new Product();
        product.setName((String) attributes.get("name"));
        product.setPrice((Double) attributes.get("price"));
        Product savedProduct = productService.saveProduct(product);
        return Map.of(
                "data", Map.of(
                        "type", "products",
                        "id", savedProduct.getId().toString(),
                        "attributes", Map.of(
                                "name", savedProduct.getName(),
                                "price", savedProduct.getPrice()
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return Map.of("meta", Map.of("message", "Product deleted successfully"));
    }
}
