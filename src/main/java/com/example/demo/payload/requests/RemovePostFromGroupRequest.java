package com.example.demo.payload.requests;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RemovePostFromGroupRequest {
    @GraphQLField
    private String groupId;
    @GraphQLField
    private String postId;
}
