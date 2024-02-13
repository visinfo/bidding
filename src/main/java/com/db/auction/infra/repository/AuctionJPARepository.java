package com.db.auction.infra.repository;

import com.db.auction.infra.repository.entity.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionJPARepository extends JpaRepository<AuctionEntity, Long> {
}
