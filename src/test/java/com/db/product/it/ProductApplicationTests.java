package com.db.product.it;

import com.db.BiddingApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("test")
@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = {BiddingApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductApplicationTests {
    @Autowired
    public WebTestClient webTestClient;

    @Test
    void whenGetProductDetailsWithInvalidProductId_thenReturnBadRequest() {
        webTestClient.get().uri("/products/XYS").exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void whenGetProductDetailsWithValidProductId_thenReturnProduct() {
        webTestClient.post().uri("/products/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"id\":1,\"name\":\"Product 1\",\"description\":\"Product 1 description\"}")
                .exchange()
                .expectStatus()
                .isOk();
        webTestClient.get().uri("/products/1").exchange()
                .expectStatus()
                .isOk();
    }
}
