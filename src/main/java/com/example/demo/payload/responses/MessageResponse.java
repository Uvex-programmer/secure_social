package com.example.demo.payload.responses;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MessageResponse {
    @GraphQLField
    private String message;
}
