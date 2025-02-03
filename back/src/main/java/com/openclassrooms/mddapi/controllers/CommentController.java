package com.openclassrooms.mddapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.mappers.CommentMapper;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.UserService;

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
    private final UserService userService;

    CommentController(CommentService commentService, CommentMapper commentMapper,
            UserService userService) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
        this.userService = userService;
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
        User user = this.userService.findById(Long.valueOf(commentDto.getUserId()));
        if (!this.userService.isCurrentUserAuthorized(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Comment comment = this.commentMapper.toEntity(commentDto);
        Comment savedComment = this.commentService.save(comment);

        return ResponseEntity.ok().body(this.commentMapper.toDto(savedComment));
    }

}
