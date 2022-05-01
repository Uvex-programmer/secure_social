package com.example.demo.controllers;

import com.example.demo.Utils.JSONUtils;
import com.example.demo.graphql.RepositorySchema;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;


import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

import java.util.Map;

@RestController
public class GraphqlController {


    private final RepositorySchema repositorySchema;

    @Autowired
    GraphqlController(RepositorySchema repositorySchema) throws IOException {
        this.repositorySchema = repositorySchema;
    }

    @PostMapping(value = "/graphql")
    public ExecutionResult graphQL(@RequestBody String query) throws JSONException {

            JSONObject requestQuery = new JSONObject(query);
            String queryString = requestQuery.getString("query");

            Map<String, Object> variableMap = new HashMap<>();
            if (requestQuery.has("variables")) {
                variableMap = JSONUtils.toMap(requestQuery.getJSONObject("variables"));
            }
            return repositorySchema.execute(queryString, variableMap);
    }
}
