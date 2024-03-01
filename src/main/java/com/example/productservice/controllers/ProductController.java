package com.example.productservice.controllers;

import com.example.productservice.dtos.CreateProductRequestDto;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;

    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody CreateProductRequestDto request) {
       return productService.createProduct(request.getTitle(), request.getDescription(), request.getCategory(), request.getPrice(), request.getImage());
    }

    @GetMapping("/products/{id}")
    public Product getProductDetails(@PathVariable("id") Long productId) {
        return productService.getSingleProduct(productId);
    }

    @GetMapping("/products/category/{category}")
    public List<Product> getProducts(@PathVariable("category") String category){
        return productService.getProducts(category);
    }

    @GetMapping("/products/categories")
    public List<Category> getCategories(){
        return productService.getCategories();
    }

    public void getAllProducts() {
    }

    public void updateProduct() {
    }

}
