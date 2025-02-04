package com.openclassrooms.mddapi.services;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.exception.NoEntryFoundException;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

/* Service for Post treatment. Handle get and create post actions */
@Service
@Slf4j
public class PostService {
    private final PostRepository postRepository;

    public PostService(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * Saves the given post in database.
     *
     * @param post the post to be saved
     * @return the saved post
     */
    public Post save(final Post post) {
        log.info("Post saved");
        return postRepository.save(post);
    }

    /**
     * Retrieve a post by its id
     * 
     * @param id the id of the post to retrieve
     * @return the post with the given id
     */
    public Post findById(final Long id) {
        return this.postRepository.findById(id).orElseThrow(() -> new NoEntryFoundException("The post does not exist"));
    }
}
