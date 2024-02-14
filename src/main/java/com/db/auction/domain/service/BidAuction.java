package com.db.auction.domain.service;

import com.db.auction.domain.model.Winner;

import java.math.BigDecimal;
import java.util.Optional;

public interface BidAuction<A,W> {

    Optional<A> getAuction(Long id);
    Long createAuction(String productId, BigDecimal minimumBid, String title);
    void placeBid(Long auctionId, String bidderId, BigDecimal amount);

    Optional<A> endAuction(Long auctionId);

    Optional<W> getWinner(Long auctionId);
}
