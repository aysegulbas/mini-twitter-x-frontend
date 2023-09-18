package com.workintech.twitterApp.controller;

import com.workintech.twitterApp.dto.LoginRequest;
import com.workintech.twitterApp.dto.LoginResponse;
import com.workintech.twitterApp.dto.RegistrationUser;
import com.workintech.twitterApp.entity.User;
import com.workintech.twitterApp.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationService authenticationService;

    @Autowired

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/register")
    public User register(@RequestBody RegistrationUser registrationUser){
        return authenticationService.register(registrationUser.getUsername(),registrationUser.getEmail(),registrationUser.getPassword());
//register metotunun içinde zaten passwordu encode ediyor, direkt password yazdık
        //securityconfig'de instance tanımladık,authenticationservice'de dependency injection ile yakalamış oldu
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        return authenticationService.login(loginRequest.getEmail(),
                loginRequest.getPassword());
    }
}
