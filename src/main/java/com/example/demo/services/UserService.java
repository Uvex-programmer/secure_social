package com.example.demo.services;

import com.example.demo.models.User;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();
    Optional<User> findOneById(ObjectId id);
    List<User> findByIdIn(List<String>ids);
    Optional<User> findByUsername(String username);
}
