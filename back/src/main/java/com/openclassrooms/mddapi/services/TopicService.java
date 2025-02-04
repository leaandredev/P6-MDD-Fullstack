package com.openclassrooms.mddapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.exception.NoEntryFoundException;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

/**
 * Service for Topic treatment. Handle get topics actions
 */
@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    TopicService(final TopicRepository topicRepository, final UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieve all topics
     * 
     * @return a list of all topics
     */
    public List<Topic> findAll() {
        return this.topicRepository.findAll();
    }

    /**
     * Retrieve a topic by its id
     * 
     * @param id the id of the topic to retrieve
     * @return the topic with the given id
     */
    public Topic findById(final Long id) {
        return this.topicRepository.findById(id).orElse(null);
    }

    /**
     * Subscribes a user to a topic.
     *
     * @param topicId the ID of the topic to subscribe to
     * @param userId  the ID of the user who wants to subscribe
     * @throws NoEntryFoundException if the topic or user is not found
     */
    public void subscribe(final Long topicId, final Long userId) {
        final Topic topic = this.topicRepository.findById(topicId).orElse(null);
        User user = this.userRepository.findById(userId).orElse(null);
        if (topic == null || user == null) {
            throw new NoEntryFoundException("User or topic not found");
        }

        if (user.getSubscriptions().contains(topic)) {
            return;
        }

        user.getSubscriptions().add(topic);
        this.userRepository.save(user);
    }

    /**
     * Unsubscribes a user to a topic.
     *
     * @param topicId the ID of the topic to unsubscribe to
     * @param userId  the ID of the user who wants to unsubscribe
     * @throws NoEntryFoundException if the topic or user is not found
     */
    public void unsubscribe(final Long topicId, final Long userId) {
        final Topic topic = this.topicRepository.findById(topicId).orElse(null);
        User user = this.userRepository.findById(userId).orElse(null);
        if (topic == null || user == null) {
            throw new NoEntryFoundException("User or topic not found");
        }

        if (!user.getSubscriptions().contains(topic)) {
            return;
        }

        user.getSubscriptions().remove(topic);
        this.userRepository.save(user);

    }
}
