package com.db.auction.infra.repository.entity;

import com.db.auction.domain.model.Auction;
import com.db.auction.domain.model.AuctionStatus;
import com.db.auction.domain.model.Bid;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "auction")
@SequenceGenerator(name = "events_id_generator", sequenceName = "events_id_seq", allocationSize = 1)
@Data
@NoArgsConstructor
public class AuctionEntity {

    @jakarta.persistence.Id
    @Column(name = "id",nullable = false)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="events_id_generator")
    private Long id;

    @Column(name = "product_Id", nullable = false)
    private String productId;

    @Column(name = "minimum_bid", nullable = false)
    private BigDecimal minimumBid;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    private List<BidEntity> bids;

    @Column(name = "winner_id")
    private String winnerId;

    @Column(name = "status", nullable = false)
    private String status;

    public AuctionEntity(Auction auction){
        this.productId = auction.getProductId();
        this.minimumBid = auction.getMinimumBid();
        this.status = auction.getStatus().name();
        if (auction.getBids() != null && !auction.getBids().isEmpty()) {
            this.bids = convertToBidEntities(auction.getBids());
        }

    }

    public List<BidEntity> convertToBidEntities(List<Bid> bids) {
        return bids.stream()
                .map(bid -> new BidEntity().convertToBidEntity(bid))
                .collect(Collectors.toList());
    }
    public Auction convertToAuction() {
        Auction auction = new Auction();
        auction.setProductId(this.productId);
        auction.setMinimumBid(this.minimumBid);
        auction.setWinnerId(this.winnerId);
        auction.setId(this.id);
        auction.setStatus(AuctionStatus.valueOf(this.status));
        if (this.bids != null && !this.bids.isEmpty()) {
            auction.setBids(convertToBids());
        }
        return auction;
    }

    private List<Bid> convertToBids() {
        return this.bids.stream()
                .map(BidEntity::convertToBid)
                .collect(Collectors.toList());
    }
}

