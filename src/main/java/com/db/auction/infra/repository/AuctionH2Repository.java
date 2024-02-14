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
        AuctionEntity auctionEntity=new AuctionEntity(auction);
        return auctionJPARepository.saveAndFlush(auctionEntity).getId();
    }
    public  Long saveBid(Auction auction){
        Optional<AuctionEntity> auctionEntity = auctionJPARepository.findById(auction.getId());
        if(auctionEntity.isPresent()){
            AuctionEntity updatedAuctionEntity = auctionEntity.get();
            if (auction.getBids() != null && !auction.getBids().isEmpty()) {
                updatedAuctionEntity.setBids(updatedAuctionEntity.convertToBidEntities(auction.getBids()));
            }
            return auctionJPARepository.saveAndFlush(updatedAuctionEntity).getId();

        }else{
            throw  new RuntimeException("Auction not found");
        }

    }
    public  Long endAuction(Auction auction){
        Optional<AuctionEntity> auctionEntity = auctionJPARepository.findById(auction.getId());
        if(auctionEntity.isPresent()){
            AuctionEntity updatedAuctionEntity = auctionEntity.get();
            updatedAuctionEntity.setStatus(auction.getStatus().name());
            updatedAuctionEntity.setWinnerId(auction.getWinnerId());
            return auctionJPARepository.saveAndFlush(updatedAuctionEntity).getId();

        }else{
            throw  new RuntimeException("Auction not found");
        }

    }

    @Override
    public Optional<Auction> getAuction(Long auctionId) {
        return Optional.ofNullable(auctionJPARepository.findById(auctionId).get().convertToAuction());
    }
}
