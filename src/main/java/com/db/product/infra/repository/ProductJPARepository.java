package com.db.product.infra.repository;


import com.db.product.infra.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJPARepository extends JpaRepository<ProductEntity, Long> {
}
