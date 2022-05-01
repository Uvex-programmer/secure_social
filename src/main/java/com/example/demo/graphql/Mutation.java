package com.example.demo.graphql;

import com.example.demo.dto.GroupDto;
import com.example.demo.graphql.mutations.*;
import com.example.demo.payload.responses.*;
import graphql.annotations.annotationTypes.GraphQLDataFetcher;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLMutation;
import graphql.annotations.annotationTypes.GraphQLName;

@GraphQLMutation
public class Mutation {


    /*
    * ALL ABOUT GROUPS
    */

    // Add member to a group by groupId and username.
    @GraphQLField
    @GraphQLDataFetcher(AddGroupMemberMutation.class)
    public AddMemberResponseDto addMember(@GraphQLName("groupId") String groupId, @GraphQLName("username") String username) {
        return null;
    }

    // Creation of a group.
    @GraphQLField
    @GraphQLDataFetcher(CreateGroupMutation.class)
    public GroupDto createGroup(@GraphQLName("groupName") String groupName,
                                @GraphQLName("isPrivate") boolean isPrivate) {
        return null;
    }

    // Remove member of group
    @GraphQLField
    @GraphQLDataFetcher(RemoveGroupMemberMutation.class)
    public GroupDto removeMember(
            @GraphQLName("groupId") String groupId,
            @GraphQLName("username") String username) {
        return null;
    }
    // Remove group
    @GraphQLField
    @GraphQLDataFetcher(RemoveGroupMutation.class)
    public MessageResponse removeGroup(
            @GraphQLName("groupId") String groupId) {
        return null;
    }

    //Add new posts
    @GraphQLField
    @GraphQLDataFetcher(AddNewPostToGroupMutation.class)
    public AddNewPostResponseDto addNewPostToGroup(
            @GraphQLName("groupId") String groupId,
            @GraphQLName("text") String text) {
        return null;
    }

    @GraphQLField
    @GraphQLDataFetcher(RemovePostFromGroupMutation.class)
    public RemovePostFromGroupResponseDto removePostFromGroup(
            @GraphQLName("groupId") String groupId,
            @GraphQLName("postId") String postId){
                return null;
    }

    @GraphQLField
    @GraphQLDataFetcher(EditPostInGroupMutation.class)
    public EditPostInGroupResponseDto editPostInGroup(
            @GraphQLName("groupId") String groupId,
            @GraphQLName("postId") String postId,
            @GraphQLName("text") String text) {
        return null;
    }

    @GraphQLField
    @GraphQLDataFetcher(ChangeStatusMemberMutation.class)
    public AddMemberResponseDto changeMemberStatus(
            @GraphQLName("groupId") String groupId,
            @GraphQLName("newRole") String newRole,
            @GraphQLName("username") String username) {
        return null;
    }


}
