package com.example.demo.payload.requests;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddMemberRequest {
    @GraphQLField
    private String groupId;
    @GraphQLField
    private String username;
}
