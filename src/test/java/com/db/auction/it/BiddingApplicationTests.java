package com.db.auction.it;

import com.db.BiddingApplication;
import com.db.auction.application.adapter.model.AuctionCreationRequest;
import com.db.auction.domain.BidAuctionRepository;
import com.db.auction.domain.model.Auction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = {BiddingApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BiddingApplicationTests {
    @Autowired
    public WebTestClient webTestClient;
    @MockBean
    private BidAuctionRepository auctionRepository;

    @BeforeEach
    public void setUp() {
        Auction auction = new Auction();
        auction.setId(1L);
        auction.setTitle("Auction 1");
        auction.setProductId("product_id_1");
        auction.setMinimumBid(BigDecimal.valueOf(100));
        Mockito.when(auctionRepository.getAuction(1L))
                .thenReturn(Optional.of(auction));
        Mockito.when(auctionRepository.saveAuction(any(Auction.class)))
                .thenReturn(1L);
    }

    @Test
    void whenGetAuctionWithInvalidAuctionId_thenReturnBadRequest() {
        webTestClient.get().uri("/auctions/XYS").exchange()
                .expectStatus()
                .isBadRequest();
    }
    @Test
    void whenGetAuctionWithValidAuctionId_thenReturnAuction() {
        webTestClient.get().uri("/auctions/1").exchange()
                .expectStatus()
                .isOk().expectBody().jsonPath("$.title").isEqualTo("Auction 1");
    }
    @Test
    void whenCreateAuctionWithAuctionParams_thenReturnAuctionId() {
        AuctionCreationRequest auctionCreationRequest = new AuctionCreationRequest();
        auctionCreationRequest.setTitle("Auction 1");
        auctionCreationRequest.setProductId("product_id_1");
        auctionCreationRequest.setMinimumBid(BigDecimal.valueOf(100));
        webTestClient.post().uri("/auctions/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(auctionCreationRequest).exchange()
                .expectStatus()
                .isOk().expectBody();
    }

    @Test
    void whenPlaceBidForAuction_thenAddBidToAuction() {
        webTestClient.patch().uri("/auctions/placeBid/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"bidder\":\"bidder_1\",\"bidAmount\":100}").exchange()
                .expectStatus()
                .isOk().expectBody();
    }

    @Test
    void whenEndAuction_thenEndAuction() {
        webTestClient.put().uri("/auctions/endAuction/1").exchange()
                .expectStatus()
                .isOk().expectBody();
    }

    @Test
    void whenGetWinnerForAuction_thenReturnWinner() {
        webTestClient.put().uri("/auctions/endAuction/1").exchange()
                .expectStatus()
                .isOk().expectBody();
        webTestClient.get().uri("/auctions/1/winner").exchange()
                .expectStatus()
                .isOk().expectBody();
    }
}
