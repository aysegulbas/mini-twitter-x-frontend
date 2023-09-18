package com.workintech.twitterApp.excepitons;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class TweetException extends RuntimeException{
    private HttpStatus status;
    public TweetException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}