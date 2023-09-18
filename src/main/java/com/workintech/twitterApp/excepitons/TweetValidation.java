package com.workintech.twitterApp.excepitons;

import com.workintech.twitterApp.entity.Reply;
import com.workintech.twitterApp.entity.Tweet;
import org.springframework.http.HttpStatus;

public class TweetValidation {
    public static void isIdValid(int id) {
        if(id <= 0){
            throw new TweetException("Id is not valid", HttpStatus.BAD_REQUEST);
        }
    }
    public static void checkContent(Tweet tweet){
        if(tweet.getContent()==null||tweet.getContent().isEmpty()){
            throw new TweetException("Tweet content can not be empty",HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkReplyContent(Reply reply){
        if(reply.getContent()==null||reply.getContent().isEmpty()){
            throw new TweetException("Reply content can not be empty",HttpStatus.BAD_REQUEST);
        }
    }

}
