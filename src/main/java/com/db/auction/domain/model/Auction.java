package com.db.auction.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auction {
    private Long id;
    private String title;
    private String productId;
    private BigDecimal minimumBid;
    private List<Bid> bids = new ArrayList<>();
    private String winnerId;
    private AuctionStatus status;
}