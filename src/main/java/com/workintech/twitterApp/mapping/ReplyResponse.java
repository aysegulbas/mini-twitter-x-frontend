package com.workintech.twitterApp.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponse {
    private int id;
    private int tweet_id;
    private String content;
    private String userName;
    private LocalDateTime createdAt;
}
