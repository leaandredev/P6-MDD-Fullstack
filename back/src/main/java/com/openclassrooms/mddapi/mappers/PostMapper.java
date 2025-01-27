package com.openclassrooms.mddapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.models.Post;

@Component
@Mapper(componentModel = "spring", imports = { Post.class, PostDto.class })
public interface PostMapper extends EntityMapper<PostDto, Post> {

    @Mapping(source = "topicId", target = "topic.id")
    @Mapping(source = "userId", target = "user.id")
    Post toEntity(PostDto dto);

    @Mapping(source = "topic.id", target = "topicId")
    @Mapping(source = "user.id", target = "userId")
    PostDto toDto(Post entity);
}
