package com.example.demo.services;

import com.example.demo.graphql.exceptions.InvalidInput;
import com.example.demo.mapper.Mapper;
import com.example.demo.models.Group;
import com.example.demo.models.GroupPosts;
import com.example.demo.payload.responses.AddNewPostResponseDto;
import com.example.demo.payload.responses.EditPostInGroupResponseDto;
import com.example.demo.payload.responses.RemovePostFromGroupResponseDto;
import com.example.demo.repositories.GroupPostRepository;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.AuthenticationFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final AuthService auth;


    @Autowired
    public PostService(GroupRepository groupRepository, UserRepository userRepository, AuthenticationFacade authenticationFacade, GroupPostRepository groupPostRepository, Mapper mapper, AuthService auth) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.authenticationFacade = authenticationFacade;
        this.groupPostRepository = groupPostRepository;
        this.mapper = mapper;
        this.auth = auth;
    }

    public AddNewPostResponseDto addPostToGroup(String groupId, String text) {

        try {
            Optional<Group> group = groupRepository.findById(groupId);
            if (group.isPresent()) {

                String username = auth.getLoggedInUsername();
                if(username.equals("anonymousUser"))
                    throw new InvalidInput("Nice try!", HttpStatus.BAD_REQUEST);

                GroupPosts post = groupPostRepository.save(new GroupPosts(text, username));
                group.get().addPost(post);
                groupRepository.save(group.get());
                return mapper.mapPostToGroupDto(post);
            }
            throw new InvalidInput("Could not find group", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Could not fetch group: {}", e.getMessage());
            throw e;
        }
    }

    public RemovePostFromGroupResponseDto removePostFromGroup(String postId, String groupId) {

        try {
            Optional<Group> group = groupRepository.findById(groupId);
            if (group.isPresent()) {
                String username = auth.getLoggedInUsername();
                group.get().checkPostToRemoveOrEdit(postId, username, auth.checkIfSuperAdmin(), "remove","");
                groupRepository.save(group.get());
                return null;
            }
            throw new InvalidInput("Could not find group", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Could not fetch group: {}", e.getMessage());
            throw e;
        }
    }


    public EditPostInGroupResponseDto editPost(String groupId, String text, String postId) {
        try {
            Optional<Group> group = groupRepository.findById(groupId);
            if (group.isPresent()) {

                String username = auth.getLoggedInUsername();
                groupPostRepository.save(new GroupPosts(text, username));
                group.get().checkPostToRemoveOrEdit(postId, username, auth.checkIfSuperAdmin(), "edit", text);
                groupRepository.save(group.get());
                return null;
            }
            throw new InvalidInput("Could not find group", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Could not fetch group: {}", e.getMessage());
            throw e;
        }
    }
}
