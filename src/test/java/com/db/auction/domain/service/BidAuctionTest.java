package com.db.auction.domain.service;

import com.db.auction.domain.BidAuctionRepository;
import com.db.auction.domain.exception.InvalidBid;
import com.db.auction.domain.model.Auction;
import com.db.auction.domain.model.AuctionStatus;
import com.db.auction.domain.model.Bid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BidAuctionTest {
    private FirstPriceSealedBidAuction bidAuction ;

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
        Assertions.assertNotEquals(0, bidAuction.createAuction("1",BigDecimal.valueOf(1.0), "Auction1"));
    }

    @Test
    public void givenAuctionIdBidderIdAmount_whenPlaceBid_thenReturnValidAuctionId() {
        Auction auction = new Auction();
        auction.setMinimumBid(BigDecimal.valueOf(1.0));
        auction.setProductId("1");
        auction.setStatus(AuctionStatus.STARTED);
        Mockito.when(bidAuctionRepository.saveBid(any(Auction.class))).thenReturn(1L);
        Mockito.when(bidAuctionRepository.getAuction(1L)).thenReturn(Optional.of(auction));
        bidAuction.placeBid(1L,"1",BigDecimal.valueOf(1.0));
        verify(bidAuctionRepository, times(1)).saveBid(any(Auction.class));
    }

    @Test
    public void givenAuctionIdBidderIdAmount_whenPlaceBid_thenReturnInvalidBid() {
        Auction auction = new Auction();
        auction.setMinimumBid(BigDecimal.valueOf(100));
        auction.setProductId("1");
        auction.setStatus(AuctionStatus.STARTED);
        Mockito.when(bidAuctionRepository.getAuction(1L)).thenReturn(Optional.of(auction));
        Assertions.assertThrows(InvalidBid.class, ()->bidAuction.placeBid(1L,"1",BigDecimal.valueOf(0.5)));
    }
    @Test
    public void givenAuctionId_whenEndAuction_thenReturnAuction() {
        Auction auction = new Auction();
        auction.setMinimumBid(BigDecimal.valueOf(1.0));
        auction.setProductId("1");
        List<Bid> bids = List.of(new Bid("1",BigDecimal.valueOf(1.0), LocalDateTime.now(), 1L));
        auction.setBids(bids);
        Mockito.when(bidAuctionRepository.endAuction(any(Auction.class))).thenReturn(1L);
        Mockito.when(bidAuctionRepository.getAuction(1L)).thenReturn(Optional.of(auction));
        Assertions.assertNotNull(bidAuction.endAuction(1L).orElseGet(()->null));
    }
    @Test
    public void givenAuctionId_whenGetWinner_thenReturnWinner() {
        Auction auction = new Auction();
        auction.setMinimumBid(BigDecimal.valueOf(1.0));
        auction.setProductId("1");
        List<Bid> bids = List.of(new Bid("101111",BigDecimal.valueOf(1.0), LocalDateTime.now(), 1L));
        auction.setBids(bids);
        Mockito.when(bidAuctionRepository.getAuction(1L)).thenReturn(Optional.of(auction));
        Assertions.assertNotNull(bidAuction.endAuction(1L).orElseGet(()->null));
        Assertions.assertEquals(bidAuction.getWinner(1L).get().getWinnerId(), "101111");

    }
}
