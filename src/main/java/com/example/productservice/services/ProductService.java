package com.example.productservice.services;

import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    Product createProduct(String title,String description,String category,Long price,String image);
    Product getSingleProduct(Long productId) throws ProductNotFoundException;
    List<Product> getProducts();
    List<Category> getCategories();
    List<Product> getProducts(String category);


    Product updateProduct(Long productId,Product product);

    Product deleteProduct(Long productId) throws ProductNotFoundException;



}
