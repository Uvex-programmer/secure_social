package com.example.demo.services;

import com.example.demo.mapper.Mapper;
import com.example.demo.models.Group;
import com.example.demo.models.GroupPosts;
import com.example.demo.payload.responses.AddNewPostResponseDto;
import com.example.demo.payload.responses.RemovePostFromGroupResponseDto;
import com.example.demo.repositories.GroupPostRepository;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.AuthenticationFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PostService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final GroupPostRepository groupPostRepository;
    private final Mapper mapper;

    @Autowired
    public PostService(GroupRepository groupRepository, UserRepository userRepository, AuthenticationFacade authenticationFacade, GroupPostRepository groupPostRepository, Mapper mapper) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.authenticationFacade = authenticationFacade;
        this.groupPostRepository = groupPostRepository;
        this.mapper = mapper;
    }

    public AddNewPostResponseDto addPostToGroup(String groupId, String text) {
        Optional<Group> group = groupRepository.findById(groupId);
        String username = authenticationFacade.getAuthentication().getName();
        GroupPosts post = groupPostRepository.save(new GroupPosts(text,username));
        if(group.isEmpty())return null;

        group.get().addPost(post);
        groupRepository.save(group.get());

        return mapper.mapPostToGroupDto(post);
    }

    public RemovePostFromGroupResponseDto removePostFromGroup(String postId, String groupId){
        Optional<Group> group = groupRepository.findById(groupId);
        String username = authenticationFacade.getAuthentication().getName();

        if(group.isEmpty())return null;

        group.get().removePost(postId, username);
        groupRepository.save(group.get());
        return null;
    }
}