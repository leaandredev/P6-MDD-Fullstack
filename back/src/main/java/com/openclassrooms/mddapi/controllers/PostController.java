package com.openclassrooms.mddapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.mappers.PostMapper;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.services.PostService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
@Slf4j
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
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
        log.info("PostDTO : " + postDto);
        Post post = this.postMapper.toEntity(postDto);
        log.info(" Post : " + post);
        return ResponseEntity.ok().body(this.postMapper.toDto(this.postService.save(post)));
    }

}
