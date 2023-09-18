package com.workintech.twitterApp.repository;

import com.workintech.twitterApp.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TweetRepository extends JpaRepository<Tweet,Integer> {
@Query("SELECT w FROM Tweet w WHERE w.id=:id")
Optional<Tweet> getById(int id);
}
