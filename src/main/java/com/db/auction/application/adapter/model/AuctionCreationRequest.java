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
public class AuctionCreationRequest {
    private String productId;
    private BigDecimal minimumBid;
    private String title;
}
