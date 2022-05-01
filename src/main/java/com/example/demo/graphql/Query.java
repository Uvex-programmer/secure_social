package com.example.demo.graphql;

import com.example.demo.dto.GroupDto;
import com.example.demo.payload.responses.UserJoinedGroupsResponseDto;
import graphql.annotations.annotationTypes.GraphQLDataFetcher;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import org.springframework.stereotype.Component;
import com.example.demo.graphql.fetchers.*;
import java.util.List;

@Component
public class Query {


    @GraphQLField
    @GraphQLDataFetcher(UserJoinedGroupsFetcher.class)
    public UserJoinedGroupsResponseDto getUserJoinedGroups(){
        return null;
    }

    @GraphQLField
    @GraphQLDataFetcher(AllGroupsFetcher.class)
    public List<GroupDto> getGroups(){
        return null;
    }

    @GraphQLField
    @GraphQLDataFetcher(SingleGroupFetcher.class)
    public GroupDto findSingleGroupById(@GraphQLName("groupId") String groupId){return null;}

    @GraphQLField
    @GraphQLDataFetcher(GroupAccessFetcher.class)
    public GroupDto getGroupsAccess(@GraphQLName("groupId") String groupId){return null;}
}
