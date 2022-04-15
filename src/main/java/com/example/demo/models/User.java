package com.example.demo.models;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Document(collection = "Users")
public class User {

    @Id
    @GraphQLField
    private String id;
    @NotBlank
    @Size(max = 20)
    @GraphQLField
    @Indexed(unique = true)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    @GraphQLField
    private String email;
    @NotBlank
    @Size(max = 120)
    @GraphQLField
    private String password;
    @GraphQLField
    private List<String> friendsIds;
    @DBRef
    @GraphQLField
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
