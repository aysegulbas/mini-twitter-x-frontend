package com.workintech.twitterApp.service;

import com.workintech.twitterApp.entity.Reply;
import com.workintech.twitterApp.entity.Tweet;
import com.workintech.twitterApp.repository.ReplyRepository;
import com.workintech.twitterApp.repository.TweetRepository;
import com.workintech.twitterApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyServiceImpl implements ReplyService {
    private TweetRepository tweetRepository;
    private ReplyRepository replyRepository;
    private UserRepository userRepository;

    @Autowired
    public ReplyServiceImpl(TweetRepository tweetRepository, ReplyRepository replyRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.replyRepository = replyRepository;
        this.userRepository = userRepository;
    }



    @Override
    public Reply saveReply(int id, Reply reply) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isPresent()) {
            reply.setTweet(tweet.get());
            reply.setCreatedAt(LocalDateTime.now());
            tweet.get().setReplyCount(tweet.get().getReplyCount()+1);
            return replyRepository.save(reply);
        }else{return null;}
    }

    @Override
    public Reply findById(int id) {
        Optional<Reply> foundReply = replyRepository.findById(id);
        if(foundReply.isPresent()){
            foundReply.get();
        }return null;
    }

    @Override
    public Optional<Reply> getById(int id) {
        return replyRepository.findById(id);
    }

    @Override
    public void deleteReply(int reply_id) {
        Reply foundReply = replyRepository.getById(reply_id);

            replyRepository.delete(foundReply);
            foundReply.getTweet().setReplyCount(foundReply.getTweet().getReplyCount()-1);



    }
}
