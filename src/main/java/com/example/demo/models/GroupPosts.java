package com.example.demo.models;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "GroupPosts")
public class GroupPosts {

    @Id
    @GraphQLField
    private String id;
    @NotBlank
    @Size(max = 250)
    @GraphQLField
    private String text;
    @GraphQLField
    private String username;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    @GraphQLField
    private String updatedAt; //Måste ha string här???

    public GroupPosts(String text, String username) {
        this.text = text;
        this.username = username;
    }
}
