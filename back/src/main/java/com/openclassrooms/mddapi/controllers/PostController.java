package com.openclassrooms.mddapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.mappers.PostMapper;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.services.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
@Slf4j
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    public PostController(PostService postService, PostMapper postMapper, UserService userService) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.userService = userService;
    }

    /**
     * Find a post by its id
     * 
     * @param id The id of the post to find
     * @return a {@link ResponseEntity} with {@link PostResponse} response
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
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
     * @param postDto the data transfer object containing the details of the post to
     *                be created
     * @return a ResponseEntity containing the saved post
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody PostDto postDto) {
        Post post = this.postMapper.toEntity(postDto);
        Post savedPost = this.postService.save(post);
        // Add the post to the feeds of all users who have subscribed to the topic
        this.userService.addPostToFeeds(savedPost);
        return ResponseEntity.ok().body(this.postMapper.toDto(savedPost));
    }

}
