package com.example.demo.services;

import com.example.demo.mapper.Mapper;
import com.example.demo.models.Group;
import com.example.demo.models.GroupPosts;
import com.example.demo.models.SuperAdmin;
import com.example.demo.payload.responses.AddNewPostResponseDto;
import com.example.demo.payload.responses.RemovePostFromGroupResponseDto;
import com.example.demo.repositories.GroupPostRepository;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.SuperAdminRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.AuthenticationFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final SuperAdminRepository superAdminRepository;

    @Autowired
    public PostService(GroupRepository groupRepository, UserRepository userRepository, AuthenticationFacade authenticationFacade, GroupPostRepository groupPostRepository, Mapper mapper, SuperAdminRepository superAdminRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.authenticationFacade = authenticationFacade;
        this.groupPostRepository = groupPostRepository;
        this.mapper = mapper;
        this.superAdminRepository = superAdminRepository;
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

        group.get().checkPostToRemove(postId, username, checkIfSuperAdmin(username));
        groupRepository.save(group.get());
        return null;
    }

    private boolean checkIfSuperAdmin(String username) {
        Optional<SuperAdmin> temp = superAdminRepository.findByUsername(username);
        return temp.isPresent();
    }
}
