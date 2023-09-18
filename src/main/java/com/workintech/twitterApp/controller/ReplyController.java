package com.workintech.twitterApp.controller;

import com.workintech.twitterApp.entity.Reply;
import com.workintech.twitterApp.entity.Tweet;
import com.workintech.twitterApp.entity.User;
import com.workintech.twitterApp.excepitons.TweetException;
import com.workintech.twitterApp.excepitons.TweetValidation;
import com.workintech.twitterApp.mapping.ReplyResponse;
import com.workintech.twitterApp.service.ReplyService;
import com.workintech.twitterApp.service.TweetService;
import com.workintech.twitterApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/tweet/reply")
public class ReplyController {
    private ReplyService replyService;
    private UserService userService;
    private TweetService tweetService;

    @Autowired
    public ReplyController(ReplyService replyService, UserService userService, TweetService tweetService) {
        this.replyService = replyService;
        this.userService = userService;
        this.tweetService = tweetService;
    }

    private ReplyResponse expectedReply(Reply reply) {
        ReplyResponse replyResponse = new ReplyResponse();
        replyResponse.setId(reply.getId());
        replyResponse.setTweet_id(reply.getTweet().getId());
        replyResponse.setContent(reply.getContent());
        replyResponse.setUserName(reply.getUser().getResponseUserName());
        replyResponse.setCreatedAt(reply.getCreatedAt());
        return replyResponse;
    }

    @PostMapping("/{tweet_id}")
    public ReplyResponse saveReply(@RequestBody Reply reply, @PathVariable int tweet_id) {
        TweetValidation.isIdValid(tweet_id);
        TweetValidation.checkReplyContent(reply);
        Tweet foundTweet = tweetService.findById(tweet_id);
        if (foundTweet != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User currentUser = (User) userService.loadUserByUsername(username);
            if (currentUser != null) {
                reply.setUser(currentUser);
                reply.setTweet(foundTweet);
                reply.setCreatedAt(LocalDateTime.now());
                Reply createdReply = replyService.saveReply(tweet_id, reply);
                return expectedReply(createdReply);

            }
            throw new TweetException("User is not found", HttpStatus.NOT_FOUND);

        }
        throw new TweetException("Tweet with given id is not found:" + tweet_id, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{tweet_id}/{reply_id}")
    public void delete(@PathVariable int tweet_id, @PathVariable int reply_id) {
        TweetValidation.isIdValid(tweet_id);
        TweetValidation.isIdValid(reply_id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = (User) userService.loadUserByUsername(username);
        Optional<Tweet> foundTweet = tweetService.getById(tweet_id);
        Optional<Reply> foundReply = replyService.getById(reply_id);
        if (foundTweet.isPresent()) {
            if (foundReply.isPresent()) {
                if (foundReply.get().getUser().getId() == currentUser.getId()) {
                    replyService.deleteReply(reply_id);
                    tweetService.save(foundTweet.get());

                } else {
                    throw new TweetException("User is not authorized to delete tweet", HttpStatus.BAD_REQUEST);
                }
            } else {
                throw new TweetException("Reply with given id is not found:" + reply_id, HttpStatus.NOT_FOUND);
            }
        } else {
            throw new TweetException("Tweet with given id is not found:" + tweet_id, HttpStatus.NOT_FOUND);
        }
    }
}
