package com.example.demo.graphql.fetchers;

import com.example.demo.payload.responses.UserJoinedGroupsResponseDto;
import com.example.demo.services.GroupService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserJoinedGroupsFetcher implements DataFetcher<UserJoinedGroupsResponseDto>, ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public UserJoinedGroupsResponseDto get(DataFetchingEnvironment env) {
        GroupService groupService = context.getBean(GroupService.class);
        try{
            return groupService.getUsersJoinedGroups();
        }catch(Exception e){
            log.error("Could not fetch groups from user");
            throw e;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}
