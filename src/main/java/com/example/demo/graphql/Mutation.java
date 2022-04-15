package com.example.demo.graphql;

import com.example.demo.graphql.mutations.AddGroupMemberMutation;
import com.example.demo.graphql.mutations.CreateGroupMutation;
import com.example.demo.payload.responses.AddMemberResponseDto;
import com.example.demo.payload.responses.CreateGroupResponseDto;
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
    public CreateGroupResponseDto createGroup(@GraphQLName("groupName") String groupName,
                                              @GraphQLName("isPrivate") boolean isPrivate,
                                              @GraphQLName("username") String username) {
        return null;
    }
}
