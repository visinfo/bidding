package com.db.auction.domain.service;

import com.db.auction.domain.BidAuctionRepository;
import com.db.auction.domain.exception.InvalidAuction;
import com.db.auction.domain.exception.InvalidBid;
import com.db.auction.domain.model.Auction;
import com.db.auction.domain.model.Bid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class FirstPriceSealedBidAuction implements BidAuction<Auction>{
    private final BidAuctionRepository bidAuctionRepository;

    public FirstPriceSealedBidAuction(BidAuctionRepository bidAuctionRepository) {
        this.bidAuctionRepository = bidAuctionRepository;
    }
    @Override
    public Optional<Auction> getAuction(Long id) {
        return bidAuctionRepository.getAuction(id);
    }

    @Override
    public Long createAuction(String productId, BigDecimal minimumBid) {
        Auction auction = new Auction();
        auction.setMinimumBid(minimumBid);
        auction.setProductId(productId);
        return bidAuctionRepository.saveAuction(auction);
    }

    @Override
    public void placeBid(Long auctionId, String bidderId, BigDecimal amount)  {
        Optional<Auction> auction = getAuction(auctionId);
        if (auction.isEmpty()) {
            throw new InvalidAuction("Auction not found");
        }

        if (auction.get().getMinimumBid().compareTo(amount) > 0){
            throw new InvalidBid("Invalid Bid amount");
        }

        Bid bid = new Bid(bidderId, amount,  LocalDateTime.now(), auctionId);
        Auction updatedAuction = auction.get();
        updatedAuction.getBids().add(bid);
        bidAuctionRepository.saveAuction(updatedAuction);
    }
}
