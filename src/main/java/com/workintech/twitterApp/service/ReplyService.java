package com.workintech.twitterApp.service;

import com.workintech.twitterApp.entity.Reply;
import com.workintech.twitterApp.entity.Tweet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ReplyService {
    //List<Reply>allRepliesforTweet(int tweet_id);
    Reply saveReply(int id,Reply reply);
    Reply findById(int id);
    void deleteReply(int reply_id);
    Optional<Reply> getById(int id);
}
