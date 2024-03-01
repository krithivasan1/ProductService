package com.example.productservice.services;

import com.example.productservice.dtos.FakeStoreProductDto;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) {
        FakeStoreProductDto fakeStoreProduct = restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, FakeStoreProductDto.class);
        // Now we need to convert the DTO to product .. as the client needs the product and not the DTO

        return fakeStoreProduct.toProduct();
    }

    @Override
    public List<Product> getProducts(String category) {

        List<Product> listProduct = new ArrayList<Product>();
        List<LinkedHashMap<String, String>> listFakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/category/" + category, List.class);
        for (LinkedHashMap<String, String> linkedHashMap : listFakeStoreProductDto) {
            Product tempProduct = new Product();
            tempProduct.setId(Integer.parseInt((String.valueOf(linkedHashMap.get("id")))));
            tempProduct.setDescription(linkedHashMap.get("description"));
            tempProduct.setTitle(linkedHashMap.get("title"));
            tempProduct.setPrice(Double.parseDouble(String.valueOf(linkedHashMap.get("price"))));
            Category tempCategory = new Category();
            tempCategory.setTitle(linkedHashMap.get("category"));
            tempProduct.setCategory(tempCategory);
            tempProduct.setImage(linkedHashMap.get("image"));
            listProduct.add(tempProduct);

        }

        return listProduct;

    }

    @Override
    public Product createProduct(String title, String description, String category, Long price, String image) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(title);
        fakeStoreProductDto.setDescription(description);
        fakeStoreProductDto.setPrice(price);
        fakeStoreProductDto.setImage(image);
        fakeStoreProductDto.setCategory(category);
        FakeStoreProductDto response = restTemplate.postForObject("https://fakestoreapi.com/products", fakeStoreProductDto, FakeStoreProductDto.class);

        return response.toProduct();
    }
}
