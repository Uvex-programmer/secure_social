package com.example.demo.services;

import com.example.demo.models.Group;
import com.example.demo.models.User;
import com.example.demo.payload.responses.AddMemberResponseDto;
import com.example.demo.payload.responses.CreateGroupResponseDto;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public AddMemberResponseDto addMemberByUsername(String groupId, String username) {
        log.debug("Adding user to group {}", groupId);
        Optional<Group> group = groupRepository.findById(groupId);
        Optional<User> user = userRepository.findByUsername(username);

        group.get().addRole("members", user.get());
        groupRepository.save(group.get());

        return new AddMemberResponseDto().setGroupId(groupId).setUsername(user.get().getUsername());
    }

    public CreateGroupResponseDto createGroup(String groupName, boolean isPrivate, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        Group group = new Group(groupName, isPrivate);
        group.addRole("admins", user.get());

        try{
            Group newGroup = groupRepository.save(group);
            log.info("Created group {} with {} as admin", newGroup.getName(), user.get().getUsername());
            return new CreateGroupResponseDto()
                    .setGroupName(newGroup.getName())
                    .setGroupId(newGroup.getId())
                    .setPrivate(isPrivate)
                    .setAdmins(newGroup.getAdmins())
                    .setModerators(newGroup.getModerators())
                    .setMembers(newGroup.getMembers());
        } catch (Exception e){
            log.error("Failed to create group {}: {}", groupName, e.getMessage());
            return null;
        }
    }
}
