package com.example.demo.controllers;

import com.example.demo.Utils.JSONUtils;
import com.example.demo.graphql_utilities.GraphqlUtility;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.ExecutionInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;


import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.HashMap;

import java.util.Map;

@CrossOrigin
@RestController
public class MainController {

    private final GraphQL graphQL;
    private final GraphqlUtility graphqlUtility;

    @Autowired
    MainController(GraphqlUtility graphqlUtility) throws IOException {
        this.graphqlUtility = graphqlUtility;
        graphQL = graphqlUtility.createGraphQlObject();
    }

    @PostMapping(value = "/graphql")
    public ExecutionResult query(@RequestBody String query) throws JSONException {

        JSONObject requestQuery = new JSONObject(query);
        String queryString = requestQuery.getString("query");

        Map<String, Object> variableMap = new HashMap<>();
        if (requestQuery.has("variables")) {
            variableMap = JSONUtils.toMap(requestQuery.getJSONObject("variables"));
        }

        return graphQL.execute(ExecutionInput.newExecutionInput().query(queryString).variables(variableMap).build());
    }
}
