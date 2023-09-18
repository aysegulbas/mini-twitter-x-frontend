package com.workintech.twitterApp.service;

import com.workintech.twitterApp.entity.Like;
import com.workintech.twitterApp.entity.Tweet;
import com.workintech.twitterApp.entity.User;
import com.workintech.twitterApp.mapping.TweetResponse;
import com.workintech.twitterApp.repository.LikeRepository;
import com.workintech.twitterApp.repository.TweetRepository;
import com.workintech.twitterApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {
    private LikeRepository likeRepository;
    private TweetRepository tweetRepository;
    private UserRepository userRepository;


    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, TweetRepository tweetRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    //tweeti bul
    //user tweeti bir kez likelayabilir
    //counterı bir artır
    @Override
    public void like(int tweet_id, int user_id) {
        Optional<Tweet> tweet = tweetRepository.findById(tweet_id);
        Optional<User> user = userRepository.findById(user_id);
        if (tweet.isPresent() && user.isPresent()) {
            Optional<Like> alreadyLiked = likeRepository.findByTweetAndUser(tweet.get().getId(), user.get().getId());
            if (!alreadyLiked.isPresent()) {
                User newUser = new User();
                newUser.setId(user_id);

                Like newlike = new Like();
                newlike.setTweet(tweet.get());
                newlike.setUser(newUser);
                likeRepository.save(newlike);
                tweet.get().setLikeCount(tweet.get().getLikeCount() + 1);
                tweetRepository.save(tweet.get());
            }
        }
    }

    @Override
    public void unLike(int tweet_id, int user_id) {
            Optional<Tweet> tweet = tweetRepository.findById(tweet_id);
        Optional<User> user = userRepository.findById(user_id);
        if (tweet.isPresent() && user.isPresent()) {
            Optional<Like> foundLike = likeRepository.findByTweetAndUser(tweet.get().getId(), user.get().getId());
            if (foundLike.isPresent()) {
                likeRepository.delete(foundLike.get());
                tweet.get().setLikeCount(tweet.get().getLikeCount() - 1);
            }
        }
    }
}
