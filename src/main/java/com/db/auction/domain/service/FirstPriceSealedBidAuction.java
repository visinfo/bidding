package com.db.auction.domain.service;

import com.db.auction.domain.BidAuctionRepository;
import com.db.auction.domain.model.Auction;

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
    public Long createAuction(String productId, double minimumBid) {
        Auction auction = new Auction();
        auction.setMinimumBid(minimumBid);
        auction.setProductId(productId);
        return bidAuctionRepository.saveAuction(auction);
    }
}
