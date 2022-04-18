package com.example.demo.mapper;

import com.example.demo.dto.GroupDto;
import com.example.demo.models.Group;
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
}
