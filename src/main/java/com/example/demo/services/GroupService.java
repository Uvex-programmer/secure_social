package com.example.demo.services;

import com.example.demo.dto.GroupDto;
import com.example.demo.graphql.exceptions.InvalidInput;
import com.example.demo.mapper.Mapper;
import com.example.demo.models.Group;
import com.example.demo.models.User;
import com.example.demo.payload.responses.AddMemberResponseDto;
import com.example.demo.payload.responses.UserJoinedGroupsResponseDto;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.AuthenticationFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        try {
            log.debug("Adding user to group {}", groupId);
            Optional<Group> group = groupRepository.findById(groupId);
            Optional<User> user = userRepository.findByUsername(username);

            if (group.isPresent() && user.isPresent()) {
                List<Group> userGroups = groupRepository.findGroupsByUser(user.get().getId());
                boolean alreadyMember = userGroups.stream().anyMatch(grp -> grp.getId().equals(groupId));

                if (alreadyMember) {
                    throw new InvalidInput("User is already a member of this group", HttpStatus.BAD_REQUEST);
                }

                group.get().addMember("members", user.get());
                groupRepository.save(group.get());

                return new AddMemberResponseDto().setGroupId(groupId).setUsername(user.get().getUsername());
            }
            throw new InvalidInput("Could not get group or user", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.warn("Could not add member with username {}", username);
            throw e;
        }
    }

    public GroupDto createGroup(String groupName, boolean isPrivate) {
        try{
            String username = authenticationFacade.getAuthentication().getName();
            Optional<User> user = userRepository.findByUsername(username);
            if(user.isPresent()) {
                Group group = new Group(groupName, isPrivate);
                group.addMember("admins", user.get());
                Group newGroup = groupRepository.save(group);

                log.info("Created group {} with {} as admin", newGroup.getName(), user.get().getUsername());
                return mapper.mapGroupToDto(newGroup);
            }
            throw new InvalidInput("Could not find logged in user", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            log.error("Failed to create group {}: {}", groupName, e.getMessage());
            return null;
        }
    }

    public UserJoinedGroupsResponseDto getUsersJoinedGroups() {
        try {
            String username = authenticationFacade.getAuthentication().getName();
            Optional<User> user = userRepository.findByUsername(username);

            if(user.isPresent()) {
                List<Group> groups = groupRepository.findGroupsByUser(user.get().getId());

                List<GroupDto> listOfGroups = groups.stream()
                        .map(grp -> mapper.mapGroupToDto(grp))
                        .collect(Collectors.toList());

                return new UserJoinedGroupsResponseDto()
                        .setGroups(listOfGroups);
            }
            throw new InvalidInput("Could not get logged in user", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            log.error("Could not fetch users groups: {}", e.getMessage());
            throw e;
        }
    }

    public GroupDto removeGroupMember(String userId, String groupId){
        try{
            Optional<Group> group = groupRepository.findById(groupId);

            if(group.isPresent()){
                group.get().removeMember(userId);
                groupRepository.save(group.get());
                log.info("User with id: {} has been removed from group with id: {} ", userId, groupId);
                return mapper.mapGroupToDto(group.get());
            }
            throw new InvalidInput("Could not find group", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Could not remove user with id: {} from group with id: {}", userId, groupId);
            throw e;
        }
    }
}
