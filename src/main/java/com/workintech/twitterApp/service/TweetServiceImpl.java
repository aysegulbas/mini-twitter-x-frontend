package com.workintech.twitterApp.service;

import com.workintech.twitterApp.entity.Tweet;
import com.workintech.twitterApp.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TweetServiceImpl implements TweetService {
    private TweetRepository tweetRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }

    @Override
    public Optional<Tweet> getById(int id) {
        return tweetRepository.findById(id);
    }

    @Override
    public Tweet findById(int id) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if(tweet.isPresent()){
            return tweet.get();
        }
        return null;
    }

    @Override
    public Tweet save(Tweet tweet) {
        tweet.setCreatedAt(LocalDateTime.now());
        return tweetRepository.save(tweet);
    }

    @Override
    public void delete(Tweet tweet) {
        tweetRepository.delete(tweet);
    }
}
