package com.db.auction.domain.service;

import com.db.auction.domain.BidAuctionRepository;
import com.db.auction.domain.model.Auction;

import java.util.Optional;

public class FirstPriceSealedBidAuction implements BidAuction<Auction>{
    private final BidAuctionRepository bidAuctionRepository;

    public FirstPriceSealedBidAuction(BidAuctionRepository bidAuctionRepository) {
        this.bidAuctionRepository = bidAuctionRepository;
    }
    public Optional<Auction> getAuction(Long id) {
        return bidAuctionRepository.getAuction(id);
    }
}
