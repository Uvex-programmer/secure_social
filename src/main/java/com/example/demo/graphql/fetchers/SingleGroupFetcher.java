package com.example.demo.graphql.fetchers;

import com.example.demo.dto.GroupDto;
import com.example.demo.models.Group;
import com.example.demo.services.GroupService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@Slf4j

public class SingleGroupFetcher implements DataFetcher<GroupDto>, ApplicationContextAware {

private final GroupService groupService;
    @Autowired
    public SingleGroupFetcher(GroupService groupService) {
        this.groupService = groupService;
    }
    @Override
    public GroupDto get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {
        Map args = dataFetchingEnvironment.getArguments();

        return groupService.findOneById(String.valueOf(args.get("id")));
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
