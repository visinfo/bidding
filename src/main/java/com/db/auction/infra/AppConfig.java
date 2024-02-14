package com.db.auction.infra;

import com.db.auction.domain.BidAuctionRepository;
import com.db.auction.domain.service.FirstPriceSealedBidAuction;
import com.db.product.domain.ProductRepository;
import com.db.product.domain.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FirstPriceSealedBidAuction getAuctionService(@Autowired BidAuctionRepository bidAuctionRepository){
        return new FirstPriceSealedBidAuction(bidAuctionRepository);
    }

}
