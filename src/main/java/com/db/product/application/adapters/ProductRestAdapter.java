package com.db.product.application.adapters;

import com.db.product.application.adapters.model.ProductCreateRequest;
import com.db.product.domain.model.Product;
import com.db.product.domain.services.ProductService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/products")
public class ProductRestAdapter {
    private final ProductService productService;

    @Autowired
    public ProductRestAdapter(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping(
            value = "/{productId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE}
    )
    public ResponseEntity<Product> getProduct(@PathVariable("productId") Long productId){
        try{
            Product product =  productService.getProduct(productId);
            return new ResponseEntity<>(product,
                    HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(
                    e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping(
            value = "/",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE}
    )
    public ResponseEntity<String> createProduct(@RequestBody ProductCreateRequest productCreateRequest ){
        Product product = new Product();
        product.setName(productCreateRequest.getName());
        product.setDescription(productCreateRequest.getDescription());
        Long productId = productService.saveProduct(product);
        return new ResponseEntity<>("Product created with id: " + productId,
                HttpStatus.OK);
    }

}
