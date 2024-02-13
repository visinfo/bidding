package com.db.auction.domain.service;

import java.util.Optional;

public interface BidAuction<A> {

    Optional<A> getAuction(Long id);
    Long createAuction(String productId,double minimumBid);

}
