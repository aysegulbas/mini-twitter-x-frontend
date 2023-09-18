package com.workintech.twitterApp.service;

import com.workintech.twitterApp.entity.User;

import java.util.List;

public interface TwitterUserService {
    List<User> findAll();
    User findById(int id);
    User save(User user);
    void delete (User user);
}
