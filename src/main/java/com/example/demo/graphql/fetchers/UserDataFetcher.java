package com.example.demo.graphql.fetchers;

import com.example.demo.models.User;
import com.example.demo.services.UserService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class UserDataFetcher implements DataFetcher<User> {

    private UserService userService;

    @Autowired
    UserDataFetcher(UserService userService){
        this.userService = userService;
    }

    @Override
    public User get(DataFetchingEnvironment env) {
        Map args = env.getArguments();
        Optional<User> user = userService.findOneById(new ObjectId(String.valueOf(args.get("id"))));
        return null;
    }
}
