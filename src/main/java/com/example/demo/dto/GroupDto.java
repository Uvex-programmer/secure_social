package com.example.demo.dto;

import com.example.demo.models.GroupPosts;
import com.example.demo.models.User;

import graphql.annotations.annotationTypes.GraphQLField;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GroupDto {

    @GraphQLField
    private String id;
    @GraphQLField
    private String name;
    @GraphQLField
    private Set<GroupPosts> groupPosts;
    @GraphQLField
    private Set<User> admins;
    @GraphQLField
    private Set<User> moderators;
    @GraphQLField
    private Set<User> members;
    @GraphQLField
    private boolean isPrivate;
    @GraphQLField
    private int totalMembers;

}
