package com.example.productservice.services;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public class OwnDatabaseProductService implements ProductService{
    @Override
    public Product getSingleProduct(Long productId) {
        return null;
    }

    @Override
    public Product deleteProduct(Long productId) {
        return null;
    }

    @Override
    public List<Product> getProducts(String category) {
        return null;
    }

    @Override
    public List<Category> getCategories() {
        return null;
    }

    @Override
    public List<Product> getProducts() {
        return null;
    }

    @Override
    public Product updateProduct(Long productId,Product product) {
        return null;
    }

    @Override
    public Product createProduct(String title,String description,String category,Long price,String image) {
        return null;
    }
}
