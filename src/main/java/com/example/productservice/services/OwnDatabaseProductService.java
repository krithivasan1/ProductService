package com.example.productservice.services;

import com.example.productservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public class OwnDatabaseProductService implements ProductService{
    @Override
    public Product getSingleProduct(Long productId) {
        return null;
    }

    @Override
    public List<Product> getProducts(String category) {
        return null;
    }

    @Override
    public Product createProduct(String title,String description,String category,Long price,String image) {
        return null;
    }
}
