package com.abutua.productbackend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.abutua.productbackend.dto.ProductRequest;
import com.abutua.productbackend.dto.ProductResponse;
import com.abutua.productbackend.models.Category;
import com.abutua.productbackend.models.Product;
import com.abutua.productbackend.repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;


  public ProductResponse getById(long id) {
    Product product = productRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    return product.toDTO();
  }

  public List<ProductResponse> getAll() {
    return productRepository.findAll()
                            .stream()
                            .map(p -> p.toDTO())
                            .collect(Collectors.toList());
  }

  public ProductResponse save(ProductRequest productRequest) {
    try {
      Product product = productRepository.save(productRequest.toEntity());
      return product.toDTO();
    } catch (DataIntegrityViolationException e) {
      throw new EntityNotFoundException("Category not found");
    }
  }

  public void deleteById(long id) {
    // try {
      if(!productRepository.existsById(id)) {
        throw new EntityNotFoundException("Product not found");
      }
      productRepository.deleteById(id);
    // }
    // catch (EmptyResultDataAccessException e) {
    //   throw new EntityNotFoundException("Product not found");
    // }

  }

  public void update(long id, ProductRequest productUpdate) {
    try {
      Product product = productRepository.getReferenceById(id);

      Category category = new Category(productUpdate.getCategory().getId());

      product.setName(productUpdate.getName());
      product.setDescription(productUpdate.getDescription());
      product.setPrice(productUpdate.getPrice());
      product.setPromotion(productUpdate.isPromotion());
      product.setNewProduct(productUpdate.isNewProduct());
      product.setCategory(category);

      productRepository.save(product);
    }
    catch (EntityNotFoundException e) {
      throw new EntityNotFoundException("Product not found");
    }
    catch (DataIntegrityViolationException e) {
      throw new EntityNotFoundException("Category not found");
    }
  }

  /*public ProductResponse getDTOById(long id) {
    Product product = getById(id);
    return product.toDTO();
  }*/
}
