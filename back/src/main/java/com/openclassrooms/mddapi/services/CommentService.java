package com.openclassrooms.mddapi.services;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repository.CommentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Saves the given comment in database.
     * 
     * @param comment the comment to be saved
     * @return the saved comment
     */
    public Comment save(Comment comment) {
        this.commentRepository.save(comment);
        log.info("Comment saved");
        return comment;
    }

}
