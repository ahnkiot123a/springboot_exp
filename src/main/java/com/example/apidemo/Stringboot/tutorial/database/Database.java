package com.example.apidemo.Stringboot.tutorial.database;

import com.example.apidemo.Stringboot.tutorial.models.Product;
import com.example.apidemo.Stringboot.tutorial.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {

  //logger
  private static final Logger logger = LoggerFactory.getLogger(Database.class);

  // run when the application starts
  @Bean
  CommandLineRunner initDatabase(ProductRepository productRepository) {
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
        Product product1 = new Product("iPhone 12", 2020, 999.99,
            "https://www.apple.com/iphone-12/");
        Product product2 = new Product("iPhone 12 Pro", 2020, 1299.99,
            "https://www.apple.com/iphone-12-pro/");
        logger.info("insert data: " + productRepository.save(product1));
        logger.info("insert data: " + productRepository.save(product2));
      }
    };
  }


}
