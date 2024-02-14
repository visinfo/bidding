package com.db.auction.application.adapter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionCreationResponse {
    private Long auctionId;
    private String title;
    private String status;
    private BigDecimal minimumBid;
}
