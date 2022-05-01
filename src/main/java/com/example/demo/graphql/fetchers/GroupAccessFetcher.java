package com.example.demo.graphql.fetchers;

import com.example.demo.dto.GroupDto;
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

public class GroupAccessFetcher implements DataFetcher<GroupDto>, ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public GroupDto get(DataFetchingEnvironment env) throws Exception {
        String groupId = env.getArgument("groupId");
        GroupService groupService = context.getBean(GroupService.class);
        try{
            return groupService.findOneById(groupId);
        }catch(Exception e){
            log.error("Could not fetch group");
            throw e;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
