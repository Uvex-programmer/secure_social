package com.example.demo.graphql.mutations;

import com.example.demo.graphql.exceptions.InvalidInput;
import com.example.demo.payload.responses.RemovePostFromGroupResponseDto;
import com.example.demo.services.PostService;
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
public class RemovePostFromGroupMutation implements DataFetcher<RemovePostFromGroupResponseDto>, ApplicationContextAware {

    private static ApplicationContext context;
    @Override
    public RemovePostFromGroupResponseDto get(DataFetchingEnvironment env) throws Exception {
        String groupId = env.getArgument("groupId");
        String postId = env.getArgument("postId");

        if (!StringUtils.hasText(groupId) || !StringUtils.hasText(postId)) {
            throw new InvalidInput("Input validation error!", HttpStatus.BAD_REQUEST);
        }
        PostService postService = context.getBean(PostService.class);
        try{
            postService.removePostFromGroup(postId, groupId);
            return null;
        }
        catch (Exception e){
            log.warn("Error when removing post from group: {}", e.getMessage());
            throw new InvalidInput("Could not remove post from the group", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
