package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Getter
@Setter
@Document(collection = "Groups")
public class Group {

    @Id
    private String id;
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;
    private GroupRoom groupRoom = new GroupRoom();
    @DBRef
    private List<User> admins = new ArrayList<>();
    private Set<User> moderators = new HashSet<>();
    private Set<User> members = new HashSet<>();
    private boolean isPrivate;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;

    public Group(String name ,boolean isPrivate) {
        this.name = name;
        this.isPrivate = isPrivate;
    }

    public void addAdmin(User user) {
        this.admins.add(user);
    }
}
