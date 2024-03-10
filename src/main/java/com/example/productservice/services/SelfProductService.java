package com.example.productservice.services;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("SelfProductService")
public class SelfProductService implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            return productOptional.get();
        }
        return null;
    }

    @Override
    public Product deleteProduct(Long productId) {
        Product product = getSingleProduct(productId);
        productRepository.delete(product);
        return product;

    }

    @Override
    public List<Product> getProducts(String category) {
        Category newCategory = categoryRepository.findByTitle(category);
        return productRepository.findByCategory(newCategory);
        // another way
        // return productRepository.findByCategory_Title(category);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Long productId, Product product) {

        Product existingProduct = getSingleProduct(productId);
        if (product.getTitle() != null) {
            existingProduct.setTitle(product.getTitle());
        }
        if (product.getImage() != null) {
            existingProduct.setImage(product.getImage());
        }
        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription());
        }
        if (Double.valueOf(product.getPrice()) != 0.0) {
            existingProduct.setPrice(product.getPrice());
        }
        if (product.getCategory() != null) {
            Category newCategory = new Category();
            newCategory.setTitle(product.getCategory().getTitle());
            existingProduct.setCategory(newCategory);
        }
        productRepository.save(existingProduct);
        return existingProduct;
    }

    @Override
    public Product createProduct(String title, String description, String category, Long price, String image) {


        Product newProduct = new Product();
        newProduct.setTitle(title);
        newProduct.setDescription(description);
        Category newCategory = categoryRepository.findByTitle(category);
        if (newCategory == null) {
            newCategory = new Category();
            newCategory.setTitle(category);
            // categoryRepository.save(newCategory); - cascade will take care of it 
        }
        newProduct.setCategory(newCategory);
        newProduct.setPrice(price);
        newProduct.setImage(image);
        return productRepository.save(newProduct);

    }
}
