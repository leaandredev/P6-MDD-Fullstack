package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.mappers.CommentMapper;
import com.openclassrooms.mddapi.mappers.PostMapper;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.response.CommentResponse;
import com.openclassrooms.mddapi.payload.response.PostResponse;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.services.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
@Slf4j
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public PostController(PostService postService, PostMapper postMapper, UserService userService,
            CommentService commentService, CommentMapper commentMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.userService = userService;
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    /**
     * Find a post by its id
     * 
     * @param id The id of the post to find
     * @return a {@link ResponseEntity} with the {@link PostResponse} (post found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable("id") String id) {
        try {
            Post post = this.postService.findById(Long.valueOf(id));
            return ResponseEntity.ok().body(this.postMapper.toResponse(post));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Creates a new post.
     *
     * @param postDto the dto containing the details of the post to
     *                be created
     * @return a ResponseEntity containing the saved post
     * @return a {@link ResponseEntity} with the {@link PostDto} (post saved)
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<PostDto> create(@RequestBody PostDto postDto) {
        User user = this.userService.findById(Long.valueOf(postDto.getUserId()));
        if (!this.userService.isCurrentUserAuthorized(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Post post = this.postMapper.toEntity(postDto);
        Post savedPost = this.postService.save(post);
        // Add the post to the feeds of all users who have subscribed to the topic
        this.userService.addPostToFeeds(savedPost);
        return ResponseEntity.ok().body(this.postMapper.toDto(savedPost));
    }

    /**
     * Get all comments for a post
     * 
     * @param id The id of the post to find comments
     * @return a {@link ResponseEntity} containing all comments related to post, in
     *         a list of {@link CommentResponse}
     */
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable("id") String id) {
        try {
            Post post = this.postService.findById(Long.valueOf(id));
            List<Comment> comments = this.commentService.getPostComments(post);
            return ResponseEntity.ok().body(this.commentMapper.toResponse(comments));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
