package com.db.auction.domain;

import java.util.Optional;

public class FirstPriceSealedBidAuction implements BidAuction<Object>{

    public Optional<Object> getAuction(Long id) {
        return Optional.empty();
    }
}
