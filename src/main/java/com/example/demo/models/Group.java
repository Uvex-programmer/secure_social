package com.example.demo.models;

import com.example.demo.graphql.exceptions.InvalidInput;
import graphql.annotations.annotationTypes.GraphQLField;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Document(collection = "Groups")
public class Group {

    @Id
    @GraphQLField
    private String id;
    @NotBlank
    @Size(min = 3, max = 20)
    @GraphQLField
    private String name;
    @GraphQLField
    private Set<GroupPosts> groupPosts = new HashSet<>();
    @DBRef
    @GraphQLField
    private Set<User> admins = new HashSet<>();
    @GraphQLField
    private Set<User> moderators = new HashSet<>();
    @GraphQLField
    private Set<User> members = new HashSet<>();
    @GraphQLField
    private boolean isPrivate;
    @CreatedDate
    @GraphQLField
    private Date createdAt;
    @LastModifiedDate
    @GraphQLField
    private Date updatedAt;
    @GraphQLField
    private int totalMembers = 0;


    public Group(String name, boolean isPrivate) {
        this.name = name;
        this.isPrivate = isPrivate;
    }

    public void addMember(String role, User user) {
        incrementTotalMembers();
        switch (role) {
            case "admins" -> this.admins.add(user);
            case "moderators" -> this.moderators.add(user);
            default -> this.members.add(user);
        }
    }

    public void removeMember(String id) {
        this.members = this.members.stream()
                .filter(member -> !member.getId().equals(id))
                .collect(Collectors.toSet());
        this.moderators = this.moderators.stream()
                .filter(member -> !member.getId().equals(id))
                .collect(Collectors.toSet());
    }

    public void incrementTotalMembers() {
        this.totalMembers = totalMembers + 1;
    }

    public void decrementTotalMemers() {
        if (this.totalMembers < 1) return;
        this.totalMembers = totalMembers - 1;
    }

    public void addPost(GroupPosts post) {
        this.groupPosts.add(post);
    }

    public void editPost(String text, String postId) {
        for (GroupPosts post : this.groupPosts) {
            if (post.getId().equals(postId)) {
                post.setText(text);
                post.setUpdatedAt(new Date().toString());
                return;
            }
        }
    }

    public void checkPostToRemoveOrEdit(String postId, String username, boolean superAdmin, String action, String text) {

        List<GroupPosts> newList = this.getGroupPosts().stream()
                .filter(post -> post.getId().equals(postId) && post.getUsername().equals(username))
                .collect(Collectors.toList());
            if (checkIfModerator(username) || checkIfAdmin(username) || superAdmin || !newList.isEmpty()) {
                if (Objects.equals(action, "remove")) {
                    deletePost(postId);
                } else {
                    editPost(text, postId);
                }
                return;
            }
        throw new InvalidInput("User not accepted", HttpStatus.BAD_REQUEST);
        }

    private boolean checkIfAdmin(String username) {
        User tempUser = this.getAdmins().stream().filter(user -> user.getUsername().equals(username)).findAny()
                .orElse(null);
        return tempUser != null;
    }

    private boolean checkIfModerator(String username) {
        User tempUser = this.getModerators().stream().filter(user -> user.getUsername().equals(username)).findAny()
                .orElse(null);
        ;
        return tempUser != null;
    }

    private void deletePost(String postId) {
        this.groupPosts = this.getGroupPosts().stream()
                .filter(post -> !post.getId().equals(postId))
                .collect(Collectors.toSet());
    }
}
