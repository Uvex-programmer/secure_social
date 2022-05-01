package com.example.demo.mapper;

import com.example.demo.dto.GroupDto;
import com.example.demo.dto.UserDto;
import com.example.demo.models.Group;
import com.example.demo.models.GroupPosts;
import com.example.demo.models.User;
import com.example.demo.payload.responses.AddNewPostResponseDto;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public GroupDto mapGroupToDto(Group group){
        return new GroupDto()
                .setGroupPosts(group.getGroupPosts())
                .setAdmins(group.getAdmins())
                .setId(group.getId())
                .setMembers(group.getMembers())
                .setModerators(group.getModerators())
                .setName(group.getName())
                .setPrivate(group.isPrivate())
                .setTotalMembers(group.getTotalMembers());
    }

    public UserDto mapUserToDto(User user){
        return new UserDto()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setRoles(user.getRoles());
    }

    public AddNewPostResponseDto mapPostToGroupDto(GroupPosts post){
        return new AddNewPostResponseDto()
                .setId(post.getId())
                .setText(post.getText())
                .setUsername(post.getUsername())
                .setUpdatedAt(String.valueOf(post.getUpdatedAt()));
    }
}
