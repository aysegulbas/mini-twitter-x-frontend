package com.workintech.twitterApp.controller;

import com.workintech.twitterApp.entity.Tweet;
import com.workintech.twitterApp.entity.User;
import com.workintech.twitterApp.excepitons.TweetException;
import com.workintech.twitterApp.excepitons.TweetValidation;
import com.workintech.twitterApp.mapping.TweetResponse;
import com.workintech.twitterApp.service.TweetService;
import com.workintech.twitterApp.service.TwitterUserService;
import com.workintech.twitterApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tweet")
public class TweetController {
    private TweetService tweetService;
    private TwitterUserService twitterUserService;
    private UserService userService;

    @Autowired
    public TweetController(TweetService tweetService, TwitterUserService twitterUserService, UserService userService) {
        this.tweetService = tweetService;
        this.twitterUserService = twitterUserService;
        this.userService = userService;
    }

    private TweetResponse expectedTweet(Tweet tweet) {
        TweetResponse tweetResponse = new TweetResponse();
        tweetResponse.setId(tweet.getId());
        tweetResponse.setContent(tweet.getContent());
        tweetResponse.setUserName(tweet.getUser().getUsername());
        tweetResponse.setCreatedAt(tweet.getCreatedAt());
        tweetResponse.setLikeCount(tweet.getLikeCount());
        tweetResponse.setReplyCount(tweet.getReplyCount());
        tweetResponse.setRetweetCount(tweet.getRetweetCount());
        return tweetResponse;
    }

    @GetMapping("/")
    public List<TweetResponse> findAll() {
        List<Tweet> tweets=tweetService.findAll();
        return tweets.stream().map(this::expectedTweet).collect(Collectors.toList());

    }

    @GetMapping("/{id}")
    public TweetResponse findById(@PathVariable int id) {
        TweetValidation.isIdValid(id);
        Optional<Tweet> foundTweet = tweetService.getById(id);
        if (foundTweet.isPresent()) {
            return expectedTweet(foundTweet.get());

        } else {
            throw new TweetException("Tweet with given id is not found:" + id, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public TweetResponse save(@RequestBody Tweet tweet) {
        TweetValidation.checkContent(tweet);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = (User) userService.loadUserByUsername(username);
        if (currentUser != null) {
            tweet.setUser(currentUser);
            LocalDateTime now = LocalDateTime.now();
            tweet.setCreatedAt(now);
            Tweet newTweet = tweetService.save(tweet);
            return expectedTweet(newTweet);
        } else {
            throw new TweetException("User is not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public TweetResponse update(@RequestBody Tweet tweet, @PathVariable int id) {
        TweetValidation.isIdValid(id);
        TweetValidation.checkContent(tweet);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = (User) userService.loadUserByUsername(username);
        Optional<Tweet> foundTweet = tweetService.getById(id);
        if (foundTweet.isPresent()) {
            if (foundTweet.get().getUser().getId()==currentUser.getId()) {
                tweet.setId(id);
                tweet.setCreatedAt(LocalDateTime.now());
                tweet.setUser(foundTweet.get().getUser());
                Tweet updatedTweet = tweetService.save(tweet);
                return expectedTweet(updatedTweet);
            } else {
                throw new TweetException("User is not authorized to update tweet", HttpStatus.BAD_REQUEST);
            }

        } else {
            throw new TweetException("Tweet with given id is not found:" + id, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        TweetValidation.isIdValid(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = (User) userService.loadUserByUsername(username);
        Optional<Tweet> foundTweet = tweetService.getById(id);
        if (foundTweet.isPresent()) {
            if (foundTweet.get().getUser().getId()==currentUser.getId()) {
                tweetService.delete(foundTweet.get());
            } else {
                throw new TweetException("User is not authorized to delete tweet", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new TweetException("Tweet with given id is not found:" + id, HttpStatus.NOT_FOUND);
        }
    }
}
