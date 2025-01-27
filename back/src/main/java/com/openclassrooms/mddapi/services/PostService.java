package com.openclassrooms.mddapi.services;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

/* Service for Post treatment. Handle get and create post actions */
@Service
@Slf4j
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * Saves the given post in database.
     *
     * @param post the post to be saved
     * @return the saved post
     */
    public Post save(Post post) {
        postRepository.save(post);
        log.info("Post saved");
        return post;
    }

}
