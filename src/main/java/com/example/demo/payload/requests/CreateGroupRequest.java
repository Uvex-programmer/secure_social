package com.example.demo.payload.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class CreateGroupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;
    private boolean isPrivate;
}
