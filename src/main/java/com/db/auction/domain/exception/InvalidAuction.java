package com.db.auction.domain.exception;

public class InvalidAuction extends RuntimeException{
    public  InvalidAuction(String message){
        super(message);
    }
}
