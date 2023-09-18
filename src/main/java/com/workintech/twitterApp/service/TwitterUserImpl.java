package com.workintech.twitterApp.service;

import com.workintech.twitterApp.entity.User;
import com.workintech.twitterApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TwitterUserImpl implements TwitterUserService{
    private UserRepository userRepository;
@Autowired
    public TwitterUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        Optional<User>optionalUser=userRepository.findById(id);
        if(optionalUser.isPresent()){
            optionalUser.get();
        }
        return null;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
    userRepository.delete(user);

    }
}
