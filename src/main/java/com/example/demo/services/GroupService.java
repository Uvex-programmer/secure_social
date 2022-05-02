package com.example.demo.services;

import com.example.demo.dto.GroupDto;
import com.example.demo.graphql.exceptions.InvalidInput;
import com.example.demo.mapper.Mapper;
import com.example.demo.models.ERole;
import com.example.demo.models.Group;
import com.example.demo.models.User;
import com.example.demo.payload.responses.AddMemberResponseDto;
import com.example.demo.payload.responses.MessageResponse;
import com.example.demo.payload.responses.UserJoinedGroupsResponseDto;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.AuthenticationFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final Mapper mapper;
    private final AuthService auth;


    @Autowired
    public GroupService(GroupRepository groupRepository, UserRepository userRepository, AuthenticationFacade authenticationFacade, Mapper mapper, AuthService auth) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.authenticationFacade = authenticationFacade;
        this.mapper = mapper;
        this.auth = auth;
    }

    public AddMemberResponseDto changeMemberStatus(String groupId, String username, String newRole) {

        try {
            var group = groupRepository.findById(groupId);
            var user = userRepository.findByUsername(username);

            if (group.isEmpty() || user.isEmpty()) {
                throw new InvalidInput("Could not make the request", HttpStatus.BAD_REQUEST);
            }

            if (group.get().checkIfGroupAdmin(auth.getLoggedInUsername()) ||
                    group.get().checkIfGroupModerator(auth.getLoggedInUsername()) ||
                    auth.checkIfSuperAdmin()) {

                group.get().removeMember(user.get().getUsername());
                switch (newRole) {
                    case "Member" -> group.get().addMember("", user.get());
                    case "Moderator" -> group.get().addMember("moderators", user.get());
                }
                groupRepository.save(group.get());
            }
            return new AddMemberResponseDto()
                    .setGroupId(groupId)
                    .setUsername(username);
        } catch (Exception e) {
            throw e;
        }

    }

    public MessageResponse removeGroup(String groupId) {

        try {
            var user = auth.getLogginInUser();
            if (user.isEmpty()) {
                log.warn("Could not find a user!");
                throw new InvalidInput("Not authenticated", HttpStatus.BAD_REQUEST);
            }
            var group = groupRepository.findByGroupIdAndUserAsAdmin(groupId, user.get().getId());
            var checkIfSuperAdmin = user.get().getRoles().stream()
                    .anyMatch(role -> role.getName().equals(ERole.ROLE_ADMIN));

            if (group.isPresent() || checkIfSuperAdmin) {
                groupRepository.deleteById(groupId);
                return new MessageResponse()
                        .setMessage("Group deleted! Success");
            }
            log.warn("Failed to delete group");
            return new MessageResponse()
                    .setMessage("Failed to delete group");

        } catch (Exception e) {
            log.warn("Could not remove group!");
            return new MessageResponse()
                    .setMessage("Failed to delete group");
        }
    }

    public AddMemberResponseDto addMemberToGroup(String groupId, String username) {
        try {
            Optional<Group> group = groupRepository.findById(groupId);

            String name = null;
            if (StringUtils.hasText(username)) {
                if (group.get().checkIfGroupAdmin(auth.getLoggedInUsername())) name = username;
            } else {
                name = authenticationFacade.getAuthentication().getName();
            }

            Optional<User> user = userRepository.findByUsername(name);
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
        } catch (Exception e) {
            log.warn("Could not add member");
            throw e;
        }
    }

    public GroupDto createGroup(String groupName, boolean isPrivate) {
        try {
            Optional<User> user = auth.getLogginInUser();
            if (user.isPresent()) {
                Group group = new Group(groupName, isPrivate);
                group.addMember("admins", user.get());
                Group newGroup = groupRepository.save(group);

                log.info("Created group {} with {} as admin", newGroup.getName(), user.get().getUsername());
                return mapper.mapGroupToDto(newGroup);
            }
            throw new InvalidInput("Could not find logged in user", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Failed to create group {}: {}", groupName, e.getMessage());
            return null;
        }
    }

    public UserJoinedGroupsResponseDto getUsersJoinedGroups() {
        try {
            String username = authenticationFacade.getAuthentication().getName();
            Optional<User> user = userRepository.findByUsername(username);

            if (user.isPresent()) {
                List<Group> groups = groupRepository.findGroupsByUser(user.get().getId());

                List<GroupDto> listOfGroups = groups.stream()
                        .map(grp -> mapper.mapGroupToDto(grp))
                        .collect(Collectors.toList());

                return new UserJoinedGroupsResponseDto()
                        .setGroups(listOfGroups);
            }
            throw new InvalidInput("Could not get logged in user", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Could not fetch users groups: {}", e.getMessage());
            throw e;
        }
    }

    public List<GroupDto> getAllGroups() {
        List<Group> groups = (List<Group>) groupRepository.findAll();
        return groups.stream()
                .map(grp -> mapper.mapGroupToDto(grp))
                .collect(Collectors.toList());
    }

    public GroupDto removeGroupMember(String username, String groupId) {
        try {
            Optional<Group> group = groupRepository.findById(groupId);

            if (group.isPresent()) {
                if (auth.checkIfSuperAdmin() ||
                        group.get().checkIfGroupAdmin(auth.getLoggedInUsername()) ||
                        group.get().checkIfGroupModerator(auth.getLoggedInUsername())) {

                    group.get().removeMember(username);
                    groupRepository.save(group.get());
                    log.info("User with id: {} has been removed from group with id: {} ", username, groupId);
                    return mapper.mapGroupToDto(group.get());
                }
            }
            throw new InvalidInput("Could not find group", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Could not remove user with id: {} from group with id: {}", username, groupId);
            throw e;
        }
    }

    public GroupDto findOneById(String groupId) throws Exception {

        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent()) {
            return mapper.mapGroupToDto(group.get());
        }
        throw new InvalidInput("Could not find group", HttpStatus.NOT_FOUND);
    }
}
