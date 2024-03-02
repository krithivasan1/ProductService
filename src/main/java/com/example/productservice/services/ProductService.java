package com.example.productservice.services;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    Product getSingleProduct(Long productId);

    List<Product> getProducts(String category);

    List<Category> getCategories();

    List<Product> getProducts();

    Product updateProduct(Long productId,Product product);

    Product deleteProduct(Long productId);

    Product createProduct(String title,String description,String category,Long price,String image);


}
