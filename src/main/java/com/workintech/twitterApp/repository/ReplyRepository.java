package com.workintech.twitterApp.repository;

import com.workintech.twitterApp.entity.Reply;
import com.workintech.twitterApp.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Integer> {
    //@Query("Select r from Reply r WHERE r.tweet.id=:tweet_id")
    //List<Reply>findByTweetId(Tweet tweet_id);
}
