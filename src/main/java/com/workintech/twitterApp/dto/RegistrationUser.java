package com.workintech.twitterApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RegistrationUser {
    private String username;
    private String email;
    private String password;


}
