package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.mappers.TopicMapper;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.services.TopicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topic")
public class TopicController {

    private final TopicService topicService;
    private final TopicMapper topicMapper;

    public TopicController(TopicService topicService, TopicMapper topicMapper) {
        this.topicService = topicService;
        this.topicMapper = topicMapper;
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
     *         Returns 200 OK if the subscription is successful.
     *         Returns 400 Bad Request if the provided IDs are not valid numbers.
     */
    @PostMapping("{id}/subscribe/{userId}")
    public ResponseEntity<?> subscribe(@PathVariable("id") String id, @PathVariable("userId") String userId) {
        try {
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
     *         Returns 200 OK if the unsubscribe is successful.
     *         Returns 400 Bad Request if the provided IDs are not valid numbers.
     */
    @DeleteMapping("{id}/unsubscribe/{userId}")
    public ResponseEntity<?> unsubscribe(@PathVariable("id") String id, @PathVariable("userId") String userId) {
        try {
            this.topicService.unsubscribe(Long.parseLong(id), Long.parseLong(userId));
            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
