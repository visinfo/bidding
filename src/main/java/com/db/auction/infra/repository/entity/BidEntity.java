package com.db.auction.infra.repository.entity;
import com.db.auction.domain.model.Bid;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bid")
@Data
@NoArgsConstructor
public class BidEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auction_id", nullable = false)
    private String auctionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", insertable = false, updatable = false)
    private AuctionEntity auction;

    @Column(name = "bidder_id", nullable = false)
    private String bidderId;


    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public  BidEntity convertToBidEntity(Bid bid) {
        BidEntity bidEntity = new BidEntity();
        bidEntity.setBidderId(bid.getBidderId());
        bidEntity.setAmount(bid.getAmount());
        bidEntity.setTimestamp(bid.getTimestamp());
        bidEntity.setAuctionId(String.valueOf(bid.getAuctionId()));
        // Set other properties if needed
        return bidEntity;
    }
    public Bid convertToBid() {
        Bid bid = new Bid();
        bid.setBidderId(this.getBidderId());
        bid.setAmount(this.getAmount());
        bid.setTimestamp(this.getTimestamp());
        bid.setAuctionId(Long.parseLong(this.getAuctionId()));
        return bid;
    }

}
