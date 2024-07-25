package com.abutua.productbackend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.abutua.productbackend.dto.CategoryRequest;
import com.abutua.productbackend.dto.CategoryResponse;
import com.abutua.productbackend.models.Category;
import com.abutua.productbackend.repositories.CategoryRepository;
import com.abutua.productbackend.services.exceptions.DatabaseException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

  @Autowired
  CategoryRepository categoryRepository;
  
  public CategoryResponse getById(int id) {
    Category category = categoryRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    return category.toDTO();
  }

  public List<CategoryResponse> getAll() {
    return categoryRepository.findAll()
                              .stream()
                              .map(c -> c.toDTO())
                              .collect(Collectors.toList());
  }

  public CategoryResponse save(CategoryRequest categoryRequest) {
    Category category = categoryRepository.save(categoryRequest.toEntity());
    return category.toDTO();
  }

  public void deleteById(int id) {
    try {
      if(!categoryRepository.existsById(id)) {
        throw new EntityNotFoundException("Category not found");
      }
      categoryRepository.deleteById(id);
    }
    catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Constrain violation, can't delete category");
    }
    // catch (EmptyResultDataAccessException e) {
    //   throw new EntityNotFoundException("Category not found");
    // }
   
  }

  public void update(int id, CategoryRequest categoryUpdate) {
    try {
      Category category = categoryRepository.getReferenceById(id);
      category.setName(categoryUpdate.getName());
      categoryRepository.save(category);      
    }
    catch (EntityNotFoundException e) {
      throw new EntityNotFoundException("Category not found");
    }

  }

}
