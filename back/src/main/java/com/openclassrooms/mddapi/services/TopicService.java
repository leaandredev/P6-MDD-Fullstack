package com.openclassrooms.mddapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

/**
 * Service for Topic treatment. Handle get topics actions
 */
@Service
public class TopicService {

    private final TopicRepository topicRepository;

    TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * Retrieve all topics
     * 
     * @return a list of all topics
     */
    public List<Topic> findAll() {
        return this.topicRepository.findAll();
    }

}
