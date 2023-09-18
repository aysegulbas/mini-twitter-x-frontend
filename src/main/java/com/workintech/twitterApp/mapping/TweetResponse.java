package com.workintech.twitterApp.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetResponse {
    private int id;
    private String content;
    private String userName;
    private LocalDateTime createdAt;
    private int likeCount;
    private int retweetCount;
    private int replyCount;
    //private List<ReplyResponse>replyResponses;

}
