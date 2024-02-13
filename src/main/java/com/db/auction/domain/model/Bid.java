package com.db.auction.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bid {
    private String bidderId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private Long auctionId;


}
