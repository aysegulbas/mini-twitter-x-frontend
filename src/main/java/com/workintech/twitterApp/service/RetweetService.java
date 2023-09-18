package com.workintech.twitterApp.service;

import com.workintech.twitterApp.entity.Retweet;

import java.util.List;

public interface RetweetService {
    List<Retweet>allRetweetsForTweet(int tweet_id);
    Retweet saveRetweet(int tweet_id,int user_id);

}
