package com.example.demo.models;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
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
    private String userId;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;


}
