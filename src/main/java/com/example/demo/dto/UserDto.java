package com.example.demo.dto;

import com.example.demo.models.Role;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserDto {

    private String id;
    private String username;
    private String email;
    private Set<Role> roles;
}
