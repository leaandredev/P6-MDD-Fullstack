package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.mappers.TopicMapper;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.services.TopicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
