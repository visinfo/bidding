package com.db.product.infra;

import com.db.product.domain.ProductRepository;
import com.db.product.domain.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {

    @Bean
    public ProductService getProductService(@Autowired ProductRepository productRepository){
        return new ProductService(productRepository);
    }
}
