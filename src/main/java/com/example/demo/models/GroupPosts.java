package com.example.demo.models;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Document(collection = "GroupPosts")
public class GroupPosts {

    @Id
    @GraphQLField
    private String id;
    @DBRef
    @GraphQLField
    private List<Post> posts = new ArrayList<>();
}
