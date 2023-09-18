package com.workintech.twitterApp.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class userResponse {
    private int id;
    private String userName;
    private String email;
    //private List<TweetResponse>tweetResponses;

}
