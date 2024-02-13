package com.db.auction.service;

import com.db.auction.domain.BidAuctionRepository;
import com.db.auction.domain.model.Auction;
import com.db.auction.domain.service.BidAuction;
import com.db.auction.domain.service.FirstPriceSealedBidAuction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BidAuctionTest {
    private BidAuction bidAuction ;

    @Mock
    private BidAuctionRepository bidAuctionRepository;
    @BeforeEach
    public void setUp() {
        bidAuction = new FirstPriceSealedBidAuction(bidAuctionRepository);
    }
    @Test
    public void givenAuctionId_whenGetAuction_thenReturnAuction() {
        Mockito.when(bidAuctionRepository.getAuction(1L)).thenReturn(Optional.of(new Auction("1","1",1.0)));
        Assertions.assertNotNull(bidAuction.getAuction(1L).orElseGet(()->null));

    }

    @Test
    public void givenAuctionModel_whenCreateAuction_thenReturnAuctionId() {
        Mockito.when(bidAuction.createAuction("1",1.0)).thenReturn(1L);
        Assertions.assertNotEquals(0, bidAuction.createAuction("1",1.0));
    }
}
