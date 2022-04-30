package com.example.demo.payload.responses;

import graphql.annotations.annotationTypes.GraphQLType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@GraphQLType
@Accessors(chain = true)
public class AuthenticationResponseDto {

    private boolean authenticated;
    private String role;
}
