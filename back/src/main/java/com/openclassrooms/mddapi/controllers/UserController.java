package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
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
import com.openclassrooms.mddapi.payload.response.PostResponse;
import com.openclassrooms.mddapi.services.UserService;

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
    public ResponseEntity<UserDto> findById(@PathVariable("id") final String id) {
        try {
            final User user = this.userService.findById(Long.valueOf(id));
            if (!this.userService.isCurrentUserAuthorized(user)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
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
    public ResponseEntity<UserDto> update(@PathVariable("id") final String id, @RequestBody final UserDto userDto) {
        try {
            final User user = this.userService.findById(Long.valueOf(id));
            if (!this.userService.isCurrentUserAuthorized(user)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            final User userUpdated = this.userService.update(Long.valueOf(id), userDto);

            return ResponseEntity.ok().body(this.userMapper.toDto(userUpdated));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves the list of subscriptions (topics) for a given user.
     *
     * @param id the ID of the user whose subscriptions are to be retrieved
     * @return a {@link ResponseEntity} containing the list of {@link TopicDto} the
     *         user is subscribed to
     * 
     */
    @GetMapping("/{id}/subscriptions")
    public ResponseEntity<List<TopicDto>> getSubscriptions(@PathVariable("id") final String id) {
        try {
            final User user = this.userService.findById(Long.valueOf(id));
            if (!this.userService.isCurrentUserAuthorized(user)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            final List<Topic> topics = user.getSubscriptions();
            return ResponseEntity.ok().body(this.topicMapper.toDto(topics));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves the feed posts for a specific user.
     *
     * @param id the ID of the user whose feed posts are to be retrieved
     * @return a {@link ResponseEntity} containing the list of feed
     *         {@link PostResponse}
     */
    @GetMapping("/{id}/feed")
    public ResponseEntity<List<PostResponse>> getFeedPosts(
            @PathVariable("id") final String id,
            @RequestParam(required = false, defaultValue = "date") final String orderBy,
            @RequestParam(required = false, defaultValue = "true") final boolean asc) {
        try {
            final User user = this.userService.findById(Long.valueOf(id));
            if (!this.userService.isCurrentUserAuthorized(user)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            final List<Post> posts = this.userService.getFeedSorted(user, orderBy, asc);
            return ResponseEntity.ok().body(this.postMapper.toResponse(posts));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
