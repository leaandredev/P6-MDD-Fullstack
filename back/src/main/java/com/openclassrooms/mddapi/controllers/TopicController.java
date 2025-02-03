package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.mappers.TopicMapper;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.TopicService;
import com.openclassrooms.mddapi.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topic")
public class TopicController {

    private final TopicService topicService;
    private final TopicMapper topicMapper;
    private final UserService userService;

    public TopicController(TopicService topicService, TopicMapper topicMapper, UserService userService) {
        this.topicService = topicService;
        this.topicMapper = topicMapper;
        this.userService = userService;
    }

    /**
     * Find all topics
     * 
     * @return a {@link ResponseEntity} with a list of {@link Topic} response
     */
    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<Topic> topics = this.topicService.findAll();
        return ResponseEntity.ok().body(this.topicMapper.toDto(topics));
    }

    /**
     * Endpoint to subscribe a user to a topic.
     *
     * @param id     The ID of the topic to subscribe to.
     * @param userId The ID of the user who wants to subscribe.
     * @return ResponseEntity indicating the result of the subscription operation.
     * 
     */
    @PostMapping("{id}/subscribe/{userId}")
    public ResponseEntity<?> subscribe(@PathVariable("id") String id, @PathVariable("userId") String userId) {
        try {
            User user = this.userService.findById(Long.valueOf(userId));
            if (!this.userService.isCurrentUserAuthorized(user)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            this.topicService.subscribe(Long.parseLong(id), Long.parseLong(userId));
            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Unsubscribes a user from a topic.
     *
     * @param id     the ID of the topic to unsubscribe from
     * @param userId the ID of the user to unsubscribe
     * @return a ResponseEntity indicating the result of the operation
     * 
     */
    @DeleteMapping("{id}/unsubscribe/{userId}")
    public ResponseEntity<?> unsubscribe(@PathVariable("id") String id, @PathVariable("userId") String userId) {
        try {
            User user = this.userService.findById(Long.valueOf(userId));
            if (!this.userService.isCurrentUserAuthorized(user)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            this.topicService.unsubscribe(Long.parseLong(id), Long.parseLong(userId));
            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
