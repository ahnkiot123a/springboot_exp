package com.example.apidemo.Stringboot.tutorial.controller;

import com.example.apidemo.Stringboot.tutorial.models.Product;
import com.example.apidemo.Stringboot.tutorial.models.ResponseObject;
import com.example.apidemo.Stringboot.tutorial.repositories.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

  // DI = Dependency Injection
  @Autowired
  private ProductRepository productRepository;

  @GetMapping("")
  List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @GetMapping("/{id}")
  ResponseEntity<ResponseObject> getProductById(@PathVariable Long id) {
    Optional<Product> product = productRepository.findById(id);
    return product.map(value -> ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseObject("ok", "success", value)))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ResponseObject("not found", "product not found", "")));
  }

  // insert new Product with POST method
  @PostMapping("/insert")
  ResponseEntity<ResponseObject> insertProduct(@RequestBody Product product) {
    List<Product> foundProduct = productRepository.findByProductName(
        product.getProductName().trim());
    return foundProduct.size() > 0 ? ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseObject("created", "product created", productRepository.save(product)))
        : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
            new ResponseObject("not implemented", "Product Name already exists", ""));
  }

  // update, upsert = update if found, insert if not found
  @PutMapping("/update/{id}")
  ResponseEntity<ResponseObject> updateProduct(@RequestBody Product product,
      @PathVariable Long id) {
    Product updatedProduct = productRepository.findById(id).map(value -> {
      value.setProductName(product.getProductName());
      value.setYear(product.getYear());
      value.setPrice(product.getPrice());
      value.setUrl(product.getUrl());
      return productRepository.save(value);
    }).orElseGet(() -> {
      product.setId(id);
      return productRepository.save(product);
    });
    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseObject("ok", "product updated", updatedProduct));
  }

  // delete product by id
  @DeleteMapping("/delete/{id}")
  ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
    boolean exits = productRepository.existsById(id);
    if (exits) {
      productRepository.deleteById(id);
      return ResponseEntity.status(HttpStatus.OK)
          .body(new ResponseObject("ok", "Product deleted", ""));
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ResponseObject("not found", "Product not found", ""));
  }

}
