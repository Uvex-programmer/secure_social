package com.example.demo.models;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Roles")
public class Role {

    @Id
    @GraphQLField
    private String id;
    @GraphQLField
    private ERole name;

    public Role(){}

    public Role(ERole name) {
        this.name = name;
    }
}
