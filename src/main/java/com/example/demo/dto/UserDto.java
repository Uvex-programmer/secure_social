package com.example.demo.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserDto {

    private String id;
    private String username;
    private String email;
}
