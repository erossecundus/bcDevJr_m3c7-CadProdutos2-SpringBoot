package com.abutua.productbackend.models;

import java.io.Serializable;

import com.abutua.productbackend.dto.ProductResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="TBL_PRODUCT")
public class Product implements Serializable {
  
  // atributos
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 255)
  private String name;

  @Column(nullable = false, length = 1024)
  private String description;

  @Column(nullable = false)
  private Double price;

  private boolean promotion;
  private boolean newProduct;

  @ManyToOne
  private Category category;
  

  // metodos construtores
  public Product() {} // metodo vazio é semrpe necessário  

  // public Product(String name, String description, Double price, IntegerDTO category, boolean promotion, boolean newProduct) {
  //   this.name = name;
  //   this.description = description;
  //   this.price = price;
  //   this.category = category;
  //   this.promotion = promotion;
  //   this.newProduct = newProduct;
  // }

  public Product(Long id, String name, Double price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public Product(Long id, String name, String description, Double price, Category category, boolean promotion, boolean newProduct){
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.category = category;
    this.promotion = promotion;
    this.newProduct = newProduct;
  }  

  // metodos
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public boolean isPromotion() {
    return promotion;
  }

  public void setPromotion(boolean promotion) {
    this.promotion = promotion;
  }

  public boolean isNewProduct() {
    return newProduct;
  }

  public void setNewProduct(boolean newProduct) {
    this.newProduct = newProduct;
  }
  
  public ProductResponse toDTO() {
    ProductResponse productResponse = new ProductResponse();
    productResponse.setId(id);
    productResponse.setName(name);
    productResponse.setDescription(description);
    productResponse.setPrice(price);
    productResponse.setNewProduct(newProduct);
    productResponse.setPromotion(promotion);
    productResponse.setCategory(category.toDTO());

    return productResponse;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Product other = (Product) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", promotion="
        + promotion + ", newProduct=" + newProduct + "]";
  }

  
}
