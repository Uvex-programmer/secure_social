package com.example.demo.graphql.mutations;

import com.example.demo.dto.GroupDto;
import com.example.demo.graphql.exceptions.InvalidInput;
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
public class RemoveGroupMemberMutation implements DataFetcher<GroupDto>, ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public GroupDto get(DataFetchingEnvironment env) {
        String userId = env.getArgument("userId");
        String groupId = env.getArgument("groupId");

        if(!StringUtils.hasText(userId) || !StringUtils.hasText(groupId)){
            throw new InvalidInput("Bad input: ", HttpStatus.BAD_REQUEST);
        }
        GroupService groupService = context.getBean(GroupService.class);
        try{
            return groupService.removeGroupMember(userId, groupId);
        }catch (Exception e){
            log.warn("Error when trying to remove user: {} from group: {}", userId, groupId);
            throw new InvalidInput(String.format("Could not remove member: {} from group: {} ", userId, groupId), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
