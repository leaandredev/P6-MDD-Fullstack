package com.openclassrooms.mddapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.payload.response.CommentResponse;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.services.UserService;

@Component
@Mapper(componentModel = "spring", uses = { PostService.class, UserService.class }, imports = { Comment.class,
        CommentDto.class })
public abstract class CommentMapper
        implements EntityMapper<CommentDto, Comment>, ResponseMapper<Comment, CommentResponse> {

    @Autowired
    PostService postService;
    @Autowired
    UserService userService;

    @Mappings({
            @Mapping(target = "post", expression = "java(postService.findById(commentDto.getPostId()))"),
            @Mapping(target = "user", expression = "java(userService.findById(commentDto.getUserId()))")
    })
    public abstract Comment toEntity(CommentDto commentDto);

    @Mappings({
            @Mapping(source = "comment.post.id", target = "postId"),
            @Mapping(source = "comment.user.id", target = "userId")
    })
    public abstract CommentDto toDto(Comment comment);

    @Mappings({
            @Mapping(source = "comment.post.id", target = "postId"),
            @Mapping(source = "comment.user.userName", target = "userName")
    })
    public abstract CommentResponse toResponse(Comment comment);
}
