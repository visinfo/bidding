package com.db.auction.domain;

import com.db.auction.domain.model.Auction;

import java.util.Optional;

public interface BidAuctionRepository {
    Optional<Auction> getAuction(Long auctionId);
    Long saveAuction(Auction auction);

}
