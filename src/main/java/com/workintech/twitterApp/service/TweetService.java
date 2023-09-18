package com.workintech.twitterApp.service;

import com.workintech.twitterApp.entity.Tweet;

import java.util.List;
import java.util.Optional;

public interface TweetService {
    List<Tweet>findAll();
    Optional<Tweet> getById(int id);
    Tweet save(Tweet tweet);
    void delete (Tweet tweet);
    Tweet findById(int id);
}
