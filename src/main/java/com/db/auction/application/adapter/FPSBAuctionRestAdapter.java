package com.db.auction.application.adapter;

import com.db.auction.application.adapter.model.AuctionCreationRequest;
import com.db.auction.application.adapter.model.AuctionCreationResponse;
import com.db.auction.application.adapter.model.BidRequest;
import com.db.auction.domain.model.Auction;
import com.db.auction.domain.model.AuctionStatus;
import com.db.auction.domain.model.Winner;
import com.db.auction.domain.service.FirstPriceSealedBidAuction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/auctions")
public class FPSBAuctionRestAdapter {

    private final FirstPriceSealedBidAuction bidAuction;

    @Autowired
    public FPSBAuctionRestAdapter(FirstPriceSealedBidAuction bidAuction) {
        this.bidAuction = bidAuction;
    }

    @PostMapping("/")
    public ResponseEntity<AuctionCreationResponse> createAuction(@RequestBody AuctionCreationRequest request) {
        Long auctionId = bidAuction.createAuction(request.getProductId(), request.getMinimumBid(), request.getTitle());
        AuctionCreationResponse auctionCreationResponse = new AuctionCreationResponse(auctionId,request.getTitle(), AuctionStatus.STARTED.name(),
                request.getMinimumBid());
        return new ResponseEntity<>(auctionCreationResponse,
                HttpStatus.OK);
    }
    @GetMapping("/{auctionId}")
    public ResponseEntity<Optional<Auction>> getAuction(@PathVariable("auctionId") long auctionId) {
        try{
            Optional<Auction> auction = bidAuction.getAuction(auctionId);
            return new ResponseEntity<>(auction,
                    HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(
                    e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/endAuction/{auctionId}")
    public ResponseEntity<Optional<Auction>> endAuction(@PathVariable("auctionId") long auctionId) {
        try{
            Optional<Auction> auction = bidAuction.endAuction(auctionId);
            return new ResponseEntity<>(auction,
                    HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(
                    e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/placeBid/{auctionId}")
    public ResponseEntity<String> placeBid(@PathVariable("auctionId") long auctionId, @RequestBody BidRequest bidRequest ) {
        try{
            bidAuction.placeBid(auctionId, bidRequest.getBidderId(), BigDecimal.valueOf(bidRequest.getBidAmount()));
            return new ResponseEntity<>("Bid placed successfully",
                    HttpStatus.OK);
        }catch (RuntimeException  re){
            return new ResponseEntity(
                    re.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity(
                    e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{auctionId}/winner")
    public ResponseEntity<Optional<Winner>> getWinner(@PathVariable("auctionId") long auctionId) {
        try{
            Optional<Winner> winner = bidAuction.getWinner(auctionId);
            if(winner.isEmpty()){
                return new ResponseEntity<>(Optional.empty(),
                        HttpStatus.OK);
            }
            return new ResponseEntity<>(winner,
                    HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(
                    e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
