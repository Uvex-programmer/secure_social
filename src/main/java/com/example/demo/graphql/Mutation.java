package com.example.demo.graphql;

import com.example.demo.dto.GroupDto;
import com.example.demo.graphql.mutations.AddGroupMemberMutation;
import com.example.demo.graphql.mutations.AddPostToGroupMutation;
import com.example.demo.graphql.mutations.CreateGroupMutation;
import com.example.demo.graphql.mutations.RemoveGroupMemberMutation;
import com.example.demo.payload.responses.AddMemberResponseDto;
import com.example.demo.payload.responses.AddNewPostResponseDto;
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
    public GroupDto removeMember(@GraphQLName("groupId") String groupId,
                                @GraphQLName("userId") String userId) {
        return null;
    }

    //Add new posts
    @GraphQLField
    @GraphQLDataFetcher(AddPostToGroupMutation.class)
    public AddNewPostResponseDto addnewPost(@GraphQLName("groupId") String groupId,
                                            @GraphQLName("username") String username,
                                            @GraphQLName("text") String text) {
        return null;
    }

}
