package com.abutua.productbackend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.abutua.productbackend.dto.ProductRequest;
import com.abutua.productbackend.dto.ProductResponse;
import com.abutua.productbackend.models.Category;
import com.abutua.productbackend.models.Product;
import com.abutua.productbackend.repositories.ProductRepository;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private CategoryService categoryService;


  public Product getById(long id) {
    Product product = productRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    return product;
  }

  public List<ProductResponse> getAll() {
    return productRepository.findAll()
                            .stream()
                            .map(p -> p.toDTO())
                            .collect(Collectors.toList());
  }

  public ProductResponse save(ProductRequest productRequest) {
    Product product = productRepository.save(productRequest.toEntity());
    return product.toDTO();
  }

  public void deleteById(long id) {
    Product product = getById(id);
    productRepository.delete(product);
  }

  public void update(long id, ProductRequest productUpdate) {
    Product product = getById(id);

    if(productUpdate.getCategory() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category can not be empty");
    }
    Category category = categoryService.getById(productUpdate.getCategory().getId());

    product.setName(productUpdate.getName());
    product.setDescription(productUpdate.getDescription());
    product.setPrice(productUpdate.getPrice());
    product.setPromotion(productUpdate.isPromotion());
    product.setNewProduct(productUpdate.isNewProduct());
    product.setCategory(category);

    productRepository.save(product);
  }

  public ProductResponse getDTOById(long id) {
    Product product = getById(id);
    return product.toDTO();
  }
}
