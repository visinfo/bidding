package com.db.auction.domain.exception;

public class InvalidBid extends RuntimeException{
    public  InvalidBid(String message){
        super(message);
    }
}
