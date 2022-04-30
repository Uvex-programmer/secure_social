package com.example.demo.graphql.mutations;

import com.example.demo.graphql.exceptions.InvalidInput;
import com.example.demo.payload.responses.AddMemberResponseDto;
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
public class AddGroupMemberMutation implements DataFetcher<AddMemberResponseDto>, ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public AddMemberResponseDto get(DataFetchingEnvironment env) {
        String groupId = env.getArgument("groupId");
        String username = env.getArgument("username");


        if (!StringUtils.hasText(groupId)) {
            throw new InvalidInput("Input validation error!", HttpStatus.BAD_REQUEST);
        }
        GroupService groupService = context.getBean(GroupService.class);
        try {
            return groupService.addMemberToGroup(groupId, username);
        } catch (Exception e) {
            log.warn("Error when adding member to group: {}", e.getMessage());
            throw new InvalidInput("Could not add member to the group", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }


}
