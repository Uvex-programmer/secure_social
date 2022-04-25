package com.example.demo.services;

import com.example.demo.models.Post;
import com.example.demo.payload.responses.AddNewPostResponseDto;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public PostService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public AddNewPostResponseDto addPostToGroup(String groupId, String text, String username){


    return null;
    }
}
