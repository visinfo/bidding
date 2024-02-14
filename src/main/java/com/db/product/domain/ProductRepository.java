package com.db.product.domain;


import com.db.product.domain.model.Product;

import java.util.Optional;

public interface ProductRepository {
    Long save(Product product);
    Optional<Product> getProduct(Long productId);

}
