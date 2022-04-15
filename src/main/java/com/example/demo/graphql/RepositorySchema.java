package com.example.demo.graphql;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static graphql.GraphQL.newGraphQL;
import static graphql.annotations.AnnotationsSchemaCreator.newAnnotationsSchema;

@Component
public class RepositorySchema {

    private final Query query;
    private final GraphQL graphQL;

    @Autowired
    public RepositorySchema(Query query) {
        this.query = query;
        this.graphQL = createGraphQLSchema();
    }

    public ExecutionResult execute(String queryString, Map<String, Object> variables) {
        return graphQL.execute(ExecutionInput.newExecutionInput().query(queryString).variables(variables).context(query).build());
    }

    private GraphQL createGraphQLSchema() {
        return newGraphQL(
                newAnnotationsSchema()
                        .query(Query.class)
                        .mutation(Mutation.class)
                        .build()
        ).build();
    }
}
