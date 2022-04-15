package com.example.demo.services;

import com.example.demo.dto.GroupDto;
import com.example.demo.mapper.Mapper;
import com.example.demo.models.Group;
import com.example.demo.models.User;
import com.example.demo.payload.responses.AddMemberResponseDto;
import com.example.demo.payload.responses.CreateGroupResponseDto;
import com.example.demo.payload.responses.UserJoinedGroupsResponseDto;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.AuthenticationFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    @Autowired
    AuthenticationFacade authenticationFacade;
    @Autowired
    Mapper mapper;


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

    // TODO get username from authentication context instead. Will do later...
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

    public UserJoinedGroupsResponseDto getUsersJoinedGroups() {
        try {
            String username = authenticationFacade.getAuthentication().getName();
            Optional<User> user = userRepository.findByUsername(username);
            List<Group> groups = groupRepository.findGroupsByUser(user.get().getId());

            List<GroupDto> listOfGroups = groups.stream()
                    .map(grp -> mapper.mapGroupToDto(grp))
                    .collect(Collectors.toList());

            return new UserJoinedGroupsResponseDto()
                    .setGroups(listOfGroups);
        } catch (Exception e){
            log.error("Could not fetch users groups: {}", e.getMessage());
            throw e;
        }

    }
}
