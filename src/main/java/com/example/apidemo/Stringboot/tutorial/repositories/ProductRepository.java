package com.example.apidemo.Stringboot.tutorial.repositories;

import com.example.apidemo.Stringboot.tutorial.models.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findByProductName(String productName);

}
