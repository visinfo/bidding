package com.db.auction.domain.service;

import com.db.auction.domain.BidAuctionRepository;
import com.db.auction.domain.exception.InvalidAuction;
import com.db.auction.domain.exception.InvalidBid;
import com.db.auction.domain.model.Auction;
import com.db.auction.domain.model.AuctionStatus;
import com.db.auction.domain.model.Bid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class FirstPriceSealedBidAuction implements BidAuction<Auction>{
    private final BidAuctionRepository bidAuctionRepository;

    public FirstPriceSealedBidAuction(BidAuctionRepository bidAuctionRepository) {
        this.bidAuctionRepository = bidAuctionRepository;
    }
    @Override
    public Optional<Auction> getAuction(Long id) {
        return bidAuctionRepository.getAuction(id);
    }

    @Override
    public Long createAuction(String productId, BigDecimal minimumBid, String title) {
        Auction auction = new Auction();
        auction.setMinimumBid(minimumBid);
        auction.setProductId(productId);
        auction.setStatus(AuctionStatus.STARTED);
        auction.setTitle(title);
        return bidAuctionRepository.saveAuction(auction);
    }

    @Override
    public void placeBid(Long auctionId, String bidderId, BigDecimal amount)  {
        Optional<Auction> auction = getAuction(auctionId);
        if (auction.isEmpty()) {
            throw new InvalidAuction("Auction not found");
        }
        if (auction.get().getStatus() != AuctionStatus.STARTED) {
            throw new InvalidAuction("Auction is ended");
        }
        if (auction.get().getMinimumBid().compareTo(amount) > 0){
            throw new InvalidBid("Invalid Bid amount");
        }

        Bid bid = new Bid(bidderId, amount,  LocalDateTime.now(), auctionId);
        Auction updatedAuction = auction.get();
        updatedAuction.getBids().add(bid);
        bidAuctionRepository.saveBid(updatedAuction);
    }

    @Override
    public Optional<Auction> endAuction(Long auctionId) {
        Auction auction = getAuction(auctionId).get();
        List<Bid> bids = auction.getBids();
        if (bids.isEmpty()) {
            // No bids were placed, handle accordingly
            // For simplicity, let's assume no winner in this case
            auction.setStatus(AuctionStatus.ENDED);
            bidAuctionRepository.endAuction(auction);
            return Optional.empty();
        }
        // Find the highest bid
        Optional<Bid> winningBid = bids.stream()
                .max(Comparator.comparing(Bid::getAmount));
        // Set the winner and end the auction
        auction.setWinnerId(winningBid.get().getBidderId());
        auction.setStatus(AuctionStatus.ENDED);
        bidAuctionRepository.endAuction(auction);
        return Optional.of(auction);
    }

    public  Optional<String> getWinner(Long auctionId){
        Optional<Auction> auction = getAuction(auctionId);
        if (auction.isEmpty()) {
            throw new InvalidAuction("Auction not found");
        }
        if (auction.get().getStatus() != AuctionStatus.ENDED) {
            throw new InvalidAuction("Auction not ended");
        }
        return Optional.of(auction.get().getWinnerId());
    }
}
