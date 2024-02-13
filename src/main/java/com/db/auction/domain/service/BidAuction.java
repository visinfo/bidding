package com.db.auction.domain.service;

import java.math.BigDecimal;
import java.util.Optional;

public interface BidAuction<A> {

    Optional<A> getAuction(Long id);
    Long createAuction(String productId, BigDecimal minimumBid);
    void placeBid(Long auctionId, String bidderId, BigDecimal amount);

}
