package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.mappers.PostMapper;
import com.openclassrooms.mddapi.mappers.TopicMapper;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;
    private final TopicMapper topicMapper;

    private final PostMapper postMapper;

    public UserController(UserService userService, UserMapper userMapper, TopicMapper topicMapper,
            PostMapper postMapper) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.topicMapper = topicMapper;
        this.postMapper = postMapper;
    }

    /**
     * Find a user by its id
     * 
     * @param id The id of the user to find
     * @return a {@link ResponseEntity} with {@link UserDto} response
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        try {
            User user = this.userService.findById(Long.valueOf(id));
            return ResponseEntity.ok().body(this.userMapper.toDto(user));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update a user
     * 
     * @param id      The id of the user to update
     * @param userDto The UserDto entity to update
     * @return a {@link ResponseEntity} with {@link UserDto} response
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody UserDto userDto) {
        try {
            User user = this.userService.update(Long.valueOf(id), userDto);
            return ResponseEntity.ok().body(this.userMapper.toDto(user));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves the list of subscriptions (topics) for a given user.
     *
     * @param id the ID of the user whose subscriptions are to be retrieved
     * @return a ResponseEntity containing the list of topics the user is subscribed to,
     *         or a bad request response if the ID is not a valid number
     */
    @GetMapping("/{id}/subscriptions")
    public ResponseEntity<?> getSubscriptions(@PathVariable("id") String id) {
        try {
            User user = this.userService.findById(Long.valueOf(id));
            List<Topic> topics = user.getSubscriptions();
            return ResponseEntity.ok().body(this.topicMapper.toDto(topics));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves the feed posts for a specific user.
     *
     * @param id the ID of the user whose feed posts are to be retrieved
     * @return a ResponseEntity containing the list of feed posts in DTO format if the user is found,
     *         or a bad request response if the ID is not a valid number
     */
    @GetMapping("/{id}/feed")
    public ResponseEntity<?> getFeedPosts(@PathVariable("id") String id) {
        try {
            User user = this.userService.findById(Long.valueOf(id));
            List<Post> posts = user.getFeed();
            return ResponseEntity.ok().body(this.postMapper.toDto(posts));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
