package com.example.demo.graphql.fetchers;

import com.example.demo.models.User;
import com.example.demo.services.UserService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AllUsersDataFetcher implements DataFetcher<List<User>> {

    @Autowired
    UserService userService;

    public List<User> get(DataFetchingEnvironment env) {
        return userService.findAllUsers();
    }
}
