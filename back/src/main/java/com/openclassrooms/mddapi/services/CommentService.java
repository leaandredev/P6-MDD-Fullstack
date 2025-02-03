package com.openclassrooms.mddapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
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

    /**
     * Get all comments related to a post
     * 
     * @param post The post related to comments
     * @return a list of comments for the post
     */
    public List<Comment> getPostComments(Post post) {
        List<Comment> comments = this.commentRepository.findByPost(post);
        return comments;
    }

}
