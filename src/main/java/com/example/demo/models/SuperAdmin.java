package com.example.demo.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "SuperAdmin")
public class SuperAdmin {
    private final String username;



}
