package com.workintech.twitterApp.repository;

import com.workintech.twitterApp.entity.Like;
import com.workintech.twitterApp.entity.Tweet;
import com.workintech.twitterApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository <Like,Integer>{
    //likelanan twittleri bulcaz
//@Query("SELECT l FROM Like l WHERE l.tweet_id=:tweet_id")
    //List<Like>findByTweetId(int tweet_id);

@Query("SELECT l FROM Like l WHERE l.tweet.id=:tweet_id and l.user.id=:user_id ")
    Optional<Like>findByTweetAndUser(int tweet_id, int user_id);
}
