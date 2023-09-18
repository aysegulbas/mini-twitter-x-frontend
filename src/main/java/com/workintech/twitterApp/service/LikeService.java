package com.workintech.twitterApp.service;

import com.workintech.twitterApp.entity.Like;


public interface LikeService {
    void like(int tweet_id, int user_id);
    void unLike(int tweet_id,int user_id);

}
