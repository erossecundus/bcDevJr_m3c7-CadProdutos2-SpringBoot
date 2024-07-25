package com.abutua.productbackend.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import com.abutua.productbackend.dto.ProductRequest;
import com.abutua.productbackend.dto.ProductResponse;
import com.abutua.productbackend.models.Product;
import com.abutua.productbackend.services.ProductService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("products")
public class ProductController {

  // injetando dependencias
  @Autowired
  private ProductService productService;

  // salvar um produto
  @PostMapping
  public ResponseEntity<ProductResponse> save(@Validated @RequestBody ProductRequest productRequest){
    ProductResponse productResponse = productService.save(productRequest);

    // gerando o URI para o location - criando o produto
    URI location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(productResponse.getId())
      .toUri();

    return ResponseEntity.created(location).body(productResponse);
  }

  // buscar um produto
  @GetMapping("{id}")
  public ResponseEntity<ProductResponse> getProduct(@PathVariable long id) {  
    ProductResponse product = productService.getById(id);
    return ResponseEntity.ok(product);
  }

  // buscar todos os produtos
  @GetMapping
  public ResponseEntity<List<ProductResponse>> getProducts() {
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
  public ResponseEntity<Void> updateProduct(@PathVariable long id, @Valid @RequestBody ProductRequest productUpdate) {
    productService.update(id, productUpdate);
    return ResponseEntity.ok().build();
  }
}
