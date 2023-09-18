package com.workintech.twitterApp.service;

import com.workintech.twitterApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {//Username'e göre nasıl load edeceğimizi gösterir
        return userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not valid"));

    }
}
