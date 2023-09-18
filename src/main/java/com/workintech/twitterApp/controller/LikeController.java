package com.workintech.twitterApp.controller;

import com.workintech.twitterApp.entity.Tweet;
import com.workintech.twitterApp.entity.User;
import com.workintech.twitterApp.excepitons.TweetException;
import com.workintech.twitterApp.excepitons.TweetValidation;
import com.workintech.twitterApp.service.LikeService;
import com.workintech.twitterApp.service.ReplyService;
import com.workintech.twitterApp.service.TweetService;
import com.workintech.twitterApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tweet/like")
public class LikeController {
    private LikeService likeService;
    private UserService userService;
    private TweetService tweetService;

    @Autowired
    public LikeController(LikeService likeService, UserService userService, TweetService tweetService) {
        this.likeService = likeService;
        this.userService = userService;
        this.tweetService = tweetService;
    }

    @PostMapping("/{tweet_id}")
    public void like(@PathVariable int tweet_id) {
        TweetValidation.isIdValid(tweet_id);
        Optional<Tweet> foundTweet = tweetService.getById(tweet_id);
        if (foundTweet.isPresent()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User currentUser = (User) userService.loadUserByUsername(username);
            if(currentUser!=null){
                likeService.like(tweet_id, currentUser.getId());
            }else{throw new TweetException("User is not found", HttpStatus.NOT_FOUND);}

        } else {
            throw new TweetException("Tweet with given id is not found:" + tweet_id, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{tweet_id}")
    public void unlike(@PathVariable int tweet_id){
        TweetValidation.isIdValid(tweet_id);
        Tweet foundTweet = tweetService.findById(tweet_id);
        if(foundTweet!=null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User currentUser = (User) userService.loadUserByUsername(username);
            if(currentUser!=null){
                likeService.unLike(tweet_id, currentUser.getId());
                tweetService.save(foundTweet);

            }else {throw new TweetException("User is not found", HttpStatus.NOT_FOUND);}

        }else{ throw new TweetException("Tweet with given id is not found:" + tweet_id, HttpStatus.NOT_FOUND);}
    }
}
