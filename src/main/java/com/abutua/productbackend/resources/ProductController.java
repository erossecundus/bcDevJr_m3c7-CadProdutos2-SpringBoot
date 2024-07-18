package com.abutua.productbackend.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.abutua.productbackend.models.Product;
import com.abutua.productbackend.services.ProductService;

@RestController
@CrossOrigin
@RequestMapping("products")
public class ProductController {

  // injetando dependencias
  @Autowired
  private ProductService productService;

  // salvar um produto
  @PostMapping
  public ResponseEntity<Product> save(@RequestBody Product product){

    product = productService.save(product);

    // gerando o URI para o location - criando o produto
    URI location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(product.getId())
      .toUri();

    return ResponseEntity.created(location).body(product);
  }

  // buscar um produto
  @GetMapping("{id}")
  public ResponseEntity<Product> getProduct(@PathVariable long id) {  
    Product product = productService.getById(id);
    return ResponseEntity.ok(product);
  }

  // buscar todos os produtos
  @GetMapping
  public ResponseEntity<List<Product>> getProducts() {
    return ResponseEntity.ok(productService.getAll());
  }
  
  // remover um produto
  @DeleteMapping("{id}")
  public ResponseEntity<Product> deleteProduct(@PathVariable long id) {  
    productService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  // atualizar um produto
  @PutMapping("{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product productUpdate) {
    productService.update(id, productUpdate);
    return ResponseEntity.ok().build();
  }
}
