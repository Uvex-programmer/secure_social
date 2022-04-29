package com.example.demo.payload.requests;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditPostInGroupRequest {

    @GraphQLField
    String id;
    @GraphQLField
    @NotBlank
    @Size(max = 250)
    String text;
    @GraphQLField
    String groupId;
    @GraphQLField
    String username;


}
