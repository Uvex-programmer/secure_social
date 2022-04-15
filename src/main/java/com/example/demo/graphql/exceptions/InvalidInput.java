package com.example.demo.graphql.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidInput extends CustomGraphQLException{
    public InvalidInput(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
