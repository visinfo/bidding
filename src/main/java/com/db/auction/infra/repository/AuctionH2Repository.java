package com.db.auction.infra.repository;

import com.db.auction.domain.BidAuctionRepository;
import com.db.auction.domain.model.Auction;
import com.db.auction.infra.repository.entity.AuctionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuctionH2Repository implements BidAuctionRepository {

    private  final  AuctionJPARepository auctionJPARepository;

    @Override
    public Long saveAuction(Auction auction) {
        AuctionEntity auctionEntity = new AuctionEntity(auction);
        return auctionJPARepository.save(auctionEntity).getId();
    }

    @Override
    public Optional<Auction> getAuction(Long auctionId) {
        return Optional.ofNullable(auctionJPARepository.findById(auctionId).get().convertToAuction());
    }
}
