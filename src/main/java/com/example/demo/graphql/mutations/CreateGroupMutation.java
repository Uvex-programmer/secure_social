package com.example.demo.graphql.mutations;

import com.example.demo.graphql.exceptions.InvalidInput;
import com.example.demo.payload.responses.CreateGroupResponseDto;
import com.example.demo.services.GroupService;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@GraphQLName("mutation")
@Component
@Slf4j
public class CreateGroupMutation implements DataFetcher<CreateGroupResponseDto>, ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public CreateGroupResponseDto get(DataFetchingEnvironment env) {
        String groupName = env.getArgument("groupName");
        String username = env.getArgument("username");
        boolean isPrivate = env.getArgument("isPrivate");

        if (!StringUtils.hasText(username) || !StringUtils.hasText(groupName)) {
            throw new InvalidInput("Input validation error!", HttpStatus.BAD_REQUEST);
        }
        GroupService groupService = context.getBean(GroupService.class);

        try{
            return groupService.createGroup(groupName, isPrivate, username);
        }catch (Exception e) {
            log.warn("Error when creating group: {}:", e.getMessage());
            throw e;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
