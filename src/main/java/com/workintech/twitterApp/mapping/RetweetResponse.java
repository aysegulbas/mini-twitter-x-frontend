package com.workintech.twitterApp.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetweetResponse {
    private int id;
    private int tweet_id;
    private int user_id;
}
