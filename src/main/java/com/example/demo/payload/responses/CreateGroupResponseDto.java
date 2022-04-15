package com.example.demo.payload.responses;

import com.example.demo.models.User;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@GraphQLType
@Accessors(chain = true)
public class CreateGroupResponseDto {
    @GraphQLField
    private String groupId;
    @GraphQLField
    private String groupName;
    @GraphQLField
    private boolean isPrivate;
    @GraphQLField
    private Set<User> admins;
    @GraphQLField
    private Set<User> moderators;
    @GraphQLField
    private Set<User> members;


}
