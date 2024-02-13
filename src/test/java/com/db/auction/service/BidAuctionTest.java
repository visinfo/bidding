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


import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        Auction auction = new Auction();
        auction.setMinimumBid(BigDecimal.valueOf(1.0));
        auction.setProductId("1");
        Mockito.when(bidAuctionRepository.getAuction(1L)).thenReturn(Optional.of(auction));
        Assertions.assertNotNull(bidAuction.getAuction(1L).orElseGet(()->null));

    }

    @Test
    public void givenAuctionModel_whenCreateAuction_thenReturnAuctionId() {
        Mockito.when(bidAuctionRepository.saveAuction(any(Auction.class))).thenReturn(1L);
        Assertions.assertNotEquals(0, bidAuction.createAuction("1",BigDecimal.valueOf(1.0)));
    }

    @Test
    public void givenAuctionIdBidderIdAmount_whenPlaceBid_thenReturnValidAuctionId() {
        Auction auction = new Auction();
        auction.setMinimumBid(BigDecimal.valueOf(1.0));
        auction.setProductId("1");
        Mockito.when(bidAuctionRepository.saveAuction(any(Auction.class))).thenReturn(1L);
        Mockito.when(bidAuctionRepository.getAuction(1L)).thenReturn(Optional.of(auction));
        bidAuction.placeBid(1L,"1",BigDecimal.valueOf(1.0));
        verify(bidAuctionRepository, times(1)).saveAuction(any(Auction.class));
    }
}
