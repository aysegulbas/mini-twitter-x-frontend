package com.workintech.twitterApp.service;

import com.workintech.twitterApp.entity.Retweet;
import com.workintech.twitterApp.entity.Tweet;
import com.workintech.twitterApp.entity.User;
import com.workintech.twitterApp.repository.RetweetRepository;
import com.workintech.twitterApp.repository.TweetRepository;
import com.workintech.twitterApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RetweetServiceImpl implements RetweetService {
    private RetweetRepository retweetRepository;
    private TweetRepository tweetRepository;
    private UserRepository userRepository;

    @Autowired
    public RetweetServiceImpl(RetweetRepository retweetRepository, TweetRepository tweetRepository, UserRepository userRepository) {
        this.retweetRepository = retweetRepository;
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Retweet> allRetweetsForTweet(int tweet_id) {
        return retweetRepository.findByTweetId(tweet_id);
    }

    @Override
    public Retweet saveRetweet(int tweet_id, int user_id) {
        Optional<Tweet> tweet = tweetRepository.findById(tweet_id);
        Optional<User> user =userRepository.findById(user_id);
        if (tweet.isPresent() &&user.isPresent()) {
            User newUser=new User();
            newUser.setId(user_id);
            Retweet retweet=new Retweet();
            retweet.setTweet(tweet.get());
            retweet.setUser(newUser);
            retweet.setCreatedAt(LocalDateTime.now());
            tweet.get().setRetweetCount(tweet.get().getRetweetCount()+1);
            tweetRepository.save(tweet.get());
            return retweetRepository.save(retweet);

        }else{return null;}
    }
//retweetin deleti olmaz

}
