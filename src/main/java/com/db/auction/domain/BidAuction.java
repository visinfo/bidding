package com.db.auction.domain;

import java.util.Optional;

public interface BidAuction<A> {

    Optional<A> getAuction(Long id);
}
