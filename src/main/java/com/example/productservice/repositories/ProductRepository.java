package com.example.productservice.repositories;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
Product save(Product p);
Optional<Product> findById(Long id);

List<Product> findAll();

List<Product> findByCategory_Title(String title);

List<Product> findByCategory(Category category);

void delete(Product product);

}
