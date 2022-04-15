package com.example.demo.models;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

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
    private GroupPosts groupPosts = new GroupPosts();
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

    public Group(String name ,boolean isPrivate) {
        this.name = name;
        this.isPrivate = isPrivate;
    }

    public void addRole(String role, User user) {
        switch (role){
            case "admins" -> this.admins.add(user);
            case "moderators" -> this.moderators.add(user);
            default -> this.members.add(user);
        }
    }
}
