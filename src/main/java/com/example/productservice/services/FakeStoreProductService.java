package com.example.productservice.services;

import com.example.productservice.dtos.FakeStoreCategoryDto;
import com.example.productservice.dtos.FakeStoreProductDto;
import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.http.ResponseEntity;
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
    public Product getSingleProduct(Long productId) throws ProductNotFoundException{
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.getForEntity("https://fakestoreapi.com/products/" + productId, FakeStoreProductDto.class);
        FakeStoreProductDto fakeStoreProductDto = responseEntity.getBody();
        if(fakeStoreProductDto==null){
            throw new ProductNotFoundException("Product with Id "+productId +" not found");
        }
        return fakeStoreProductDto.toProduct();
    }

    @Override
    public Product deleteProduct(Long productId) throws ProductNotFoundException{
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", productId.toString());
        Product product = getSingleProduct(productId);
        restTemplate.delete("https://fakestoreapi.com/products/" + productId, params);
        return product;
    }

    @Override
    public List<Product> getProducts(String category) {

        List<Product> listProduct = new ArrayList<Product>();
        List<LinkedHashMap<String, String>> listFakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/category/" + category, List.class);
        for (LinkedHashMap<String, String> linkedHashMap : listFakeStoreProductDto) {
            Product tempProduct = new Product();
            tempProduct.setId(Long.parseLong((String.valueOf(linkedHashMap.get("id")))));
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
    public List<Category> getCategories() {
        List<String> fakeStoreCategoryDtoList = restTemplate.getForObject("https://fakestoreapi.com/products/categories", List.class);
        List<Category> categoryList = new ArrayList<Category>();
        for (String fakeStoreCategoryDto : fakeStoreCategoryDtoList) {
            Category tempCategory = new Category();
            tempCategory.setTitle(fakeStoreCategoryDto);
            categoryList.add(tempCategory);
        }
        return categoryList;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> listProduct = new ArrayList<Product>();
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductDto[].class);
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            listProduct.add(fakeStoreProductDto.toProduct());

        }
        return listProduct;
    }

    // another way of getting the products
    public List<Product> getProducts1() {
        List<Product> listProduct = new ArrayList<Product>();
        List<LinkedHashMap<String, String>> listFakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products", List.class);
        for (LinkedHashMap<String, String> linkedHashMap : listFakeStoreProductDto) {
            Product tempProduct = new Product();
            tempProduct.setId(Long.parseLong((String.valueOf(linkedHashMap.get("id")))));
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
    public Product updateProduct(Long productId, Product product) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", productId.toString());
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(productId.longValue());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(Double.valueOf(product.getPrice()).longValue());
        fakeStoreProductDto.setCategory(product.getCategory().getTitle());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImage());
        restTemplate.put("https://fakestoreapi.com/products/" + product.getId(), fakeStoreProductDto, params);
        return product;
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
