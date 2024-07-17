package com.abutua.productbackend.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.abutua.productbackend.models.Category;
import com.abutua.productbackend.services.CategoryService;

@RestController
@CrossOrigin
public class CategoryController {

  // Anotation para injeção de dependência
  @Autowired
  private CategoryService categoryService;

  // criar endpoint de categoria buscada
  @GetMapping("categories/{id}")
  public ResponseEntity<Category> getCategory(@PathVariable int id){
    Category category = categoryService.getById(id);
    return ResponseEntity.ok(category);
  }

  // criar endpoint de categorias
  @GetMapping("categories")
  public List<Category> getCategories(){
    return categoryService.getAll();
  }

}
