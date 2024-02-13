package com.db.auction.service;

import com.db.auction.domain.BidAuction;
import com.db.auction.domain.FirstPriceSealedBidAuction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BidAuctionTest {
    private BidAuction bidAuction ;

    @BeforeEach
    public void setUp() {
        bidAuction = new FirstPriceSealedBidAuction();
    }
    @Test
    public void givenAuctionId_whenGetAuction_thenReturnAuction() {

        Assertions.assertNotNull(bidAuction.getAuction(1L).orElseGet(()->null));

    }
}
