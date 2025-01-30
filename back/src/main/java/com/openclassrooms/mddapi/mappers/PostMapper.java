package com.openclassrooms.mddapi.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.payload.response.PostResponse;
import com.openclassrooms.mddapi.services.TopicService;
import com.openclassrooms.mddapi.services.UserService;

@Component
@Mapper(componentModel = "spring", uses = { TopicService.class, UserService.class }, imports = { Post.class,
        PostDto.class })
public abstract class PostMapper implements EntityMapper<PostDto, Post>, ResponseMapper<Post, PostResponse> {

    @Autowired
    TopicService topicService;
    @Autowired
    UserService userService;

    @Mappings({
            @Mapping(target = "topic", expression = "java(topicService.findById(postDto.getTopicId()))"),
            @Mapping(target = "user", expression = "java(userService.findById(postDto.getUserId()))")
    })
    public abstract Post toEntity(PostDto postDto);

    @Mappings({
            @Mapping(source = "post.topic.id", target = "topicId"),
            @Mapping(source = "post.user.id", target = "userId")
    })
    public abstract PostDto toDto(Post post);

    @Mappings({
            @Mapping(source = "post.topic.title", target = "topicTitle"),
            @Mapping(source = "post.user.userName", target = "userName")
    })
    public abstract PostResponse toResponse(Post post);
}
