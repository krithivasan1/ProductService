package com.example.productservice.services;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
        // JPA query method with double db calls.
        Category newCategory = categoryRepository.findByTitle(category);
        return productRepository.findByCategory(newCategory);
        // another way - Query methods with the single db call
        // return productRepository.findByCategory_Title(category);
    }

    @Override
    public Page<Product> getProductsWithPage(Integer pageSize, Integer pageNumber, String sort) {
        Pageable pageable = null;
        if (sort != null) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, sort);
        } else {
            pageable = PageRequest.of(pageNumber, pageSize);//, Sort.Direction.ASC, "price");

        }
        return productRepository.findAll(pageable);
    }

    @Override
    public boolean generateProductData() {

        List<Product> productList = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= 20; i++) {
            String title = "Computer Product " + i;
            double price = random.nextDouble(1000); // Example price (can be adjusted)
            String image = "image" + i + ".jpg"; // Example image name (can be adjusted)
            String description = "High-quality computer product #" + i + " for your computing needs."; // Example description (can be adjusted)
            String category = "Computers"; // Example category (can be adjusted)
            Category category1 = new Category();
            category1.setTitle(category);

            // Create a new ComputerProduct object and add it to the list
            productList.add(new Product(title, description, price, image, category1));
        }
        productRepository.saveAll(productList);
        return true;
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
