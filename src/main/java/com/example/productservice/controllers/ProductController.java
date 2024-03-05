package com.example.productservice.controllers;

import com.example.productservice.dtos.CreateProductRequestDto;
import com.example.productservice.exceptions.ProductNotFoundException;
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
    public Product getProductDetails(@PathVariable("id") Long productId) throws ProductNotFoundException {
        return productService.getSingleProduct(productId);
    }

    @GetMapping("/products/category/{category}")
    public List<Product> getProducts(@PathVariable("category") String category) {
        return productService.getProducts(category);
    }

    @GetMapping("/products/categories")
    public List<Category> getCategories() {
        return productService.getCategories();
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @DeleteMapping("/products/{productId}")
    public Product deleteProduct(@PathVariable("productId") Long productId) throws ProductNotFoundException {

         return productService.deleteProduct(productId);
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable("id") Long productId, @RequestBody CreateProductRequestDto request){
        Product product = new Product();
        product.setDescription(request.getDescription());
        Category category = new Category();
        category.setTitle(request.getCategory());
        product.setCategory(category);
        product.setTitle(request.getTitle());
        product.setPrice(request.getPrice());
        return productService.updateProduct(productId,product);
    }


}
