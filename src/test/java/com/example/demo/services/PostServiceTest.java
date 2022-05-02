package com.example.demo.services;

import com.example.demo.graphql.exceptions.InvalidInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {
    @Autowired
    PostService service;

    @Test
    void addPostToGroup() {

        assertThrows(NullPointerException.class, ()->{
            service.addPostToGroup("626f9ade6b0abe0f32ecce4d","testing");
        });
    }
}