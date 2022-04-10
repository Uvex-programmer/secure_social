package com.example.demo.dataFetchers;

import com.example.demo.models.User;
import com.example.demo.services.UserService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AllUsersDataFetcher implements DataFetcher<List<User>> {

    private final UserService userService;

    @Autowired
    AllUsersDataFetcher(UserService userService){
        this.userService = userService;
    }

    public List<User> get(DataFetchingEnvironment env) {
        User user =  env.getSource();
        List<User> friends;
        if(user != null){
            friends = userService.findByIdIn(user.getFriendsIds());
        }else {
            friends = userService.findAllUsers();
        }
        System.out.println(friends);
        return friends;
    }
}
