package com.example.demo.graphql.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomGraphQLException extends RuntimeException implements GraphQLError {
    private final HttpStatus httpStatus;

    public CustomGraphQLException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", String.valueOf(httpStatus));
        map.put("message", getMessage());
        return map;
    }
}
