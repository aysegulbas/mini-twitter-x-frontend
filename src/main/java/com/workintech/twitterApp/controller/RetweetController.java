package com.workintech.twitterApp.controller;

import com.workintech.twitterApp.entity.Retweet;
import com.workintech.twitterApp.entity.Tweet;
import com.workintech.twitterApp.entity.User;
import com.workintech.twitterApp.excepitons.TweetException;
import com.workintech.twitterApp.excepitons.TweetValidation;
import com.workintech.twitterApp.mapping.RetweetResponse;
import com.workintech.twitterApp.service.RetweetService;
import com.workintech.twitterApp.service.TweetService;
import com.workintech.twitterApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("tweet/retweet")
public class RetweetController {
    private RetweetService retweetService;
    private UserService userService;
    private TweetService tweetService;

    @Autowired
    public RetweetController(RetweetService retweetService, UserService userService, TweetService tweetService) {
        this.retweetService = retweetService;
        this.userService = userService;
        this.tweetService = tweetService;
    }

    private RetweetResponse exceptedRetweet(Retweet retweet) {
        RetweetResponse retweetResponse = new RetweetResponse();
        retweetResponse.setId(retweet.getId());
        retweetResponse.setTweet_id(retweet.getTweet().getId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = (User) userService.loadUserByUsername(username);
            retweetResponse.setUser_id(user.getId());
        }
        return retweetResponse;
    }

    @PostMapping("/{tweet_id}")
    public RetweetResponse createRetweet(@PathVariable int tweet_id) {
        TweetValidation.isIdValid(tweet_id);
        Optional<Tweet> foundTweet = tweetService.getById(tweet_id);
        if(foundTweet.isPresent()){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User currentUser = (User) userService.loadUserByUsername(username);
            if (currentUser != null) {
                Retweet retweet = retweetService.saveRetweet(tweet_id, currentUser.getId());
                return exceptedRetweet(retweet);
            } else {
                throw new TweetException("User is not found", HttpStatus.NOT_FOUND);
            }
        }throw new TweetException("Tweet with given id is not found:" + tweet_id, HttpStatus.NOT_FOUND);
    }
}