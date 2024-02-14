package com.db.product.infra.entity;

import com.db.product.domain.model.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "products")
@SequenceGenerator(name = "product_id_generator", sequenceName = "product_id_seq", allocationSize = 1)
@Data
@NoArgsConstructor
public class ProductEntity {
    @jakarta.persistence.Id
    @Column(name = "id",nullable = false)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="events_id_generator")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = true)
    private String description;
    public ProductEntity(Product product){
        this.name = product.getName();
        this.description = product.getDescription();
    }
    public  Product convertToProduct(){
        Product product = new Product();
        product.setId(this.id);
        product.setName(this.name);
        product.setDescription(this.description);
        return product;
    }

}
