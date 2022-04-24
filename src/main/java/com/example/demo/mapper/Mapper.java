package com.example.demo.mapper;

import com.example.demo.dto.GroupDto;
import com.example.demo.dto.UserDto;
import com.example.demo.models.Group;
import com.example.demo.models.User;
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
                .setEmail(user.getEmail());
    }
}
