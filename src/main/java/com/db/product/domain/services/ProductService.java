package com.db.product.domain.services;


import com.db.product.domain.ProductRepository;
import com.db.product.domain.model.Product;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Long saveProduct(Product product){
        return productRepository.save(product);
    }
    public Product getProduct(Long productId){
        return productRepository.getProduct(productId).orElse(null);
    }
}
