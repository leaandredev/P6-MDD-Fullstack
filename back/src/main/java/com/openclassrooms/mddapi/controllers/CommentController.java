package com.openclassrooms.mddapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.mappers.CommentMapper;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.services.CommentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    /**
     * Creates a new comment.
     * 
     * @param commentDto the dto containing the details of the comment to be created
     * @return a ResponseEntity containing the saved comment
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody CommentDto commentDto) {
        Comment comment = this.commentMapper.toEntity(commentDto);
        Comment savedComment = this.commentService.save(comment);

        return ResponseEntity.ok().body(this.commentMapper.toDto(savedComment));
    }

}
