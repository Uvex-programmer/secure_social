package com.example.demo.payload.responses;

import com.example.demo.dto.GroupDto;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@GraphQLType
@Accessors(chain = true)
public class UserJoinedGroupsResponseDto {
    @GraphQLField
    private List<GroupDto> groups;
}
