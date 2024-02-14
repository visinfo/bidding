package com.db.product.infra.repository;


import com.db.product.domain.ProductRepository;
import com.db.product.domain.model.Product;
import com.db.product.infra.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductH2Repository implements ProductRepository {

    private  final ProductJPARepository productJPARepository;

    @Override
    public Long save(Product product) {
        ProductEntity entity = new ProductEntity(product);
        return productJPARepository.save(entity).getId();
    }

    @Override
    public Optional<Product> getProduct(Long productId) {
        ProductEntity entity = productJPARepository.findById(productId).orElse(null);
        if (entity != null) {
            return Optional.of(entity.convertToProduct());
        }
        return Optional.empty();
    }
}
