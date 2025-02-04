package com.openclassrooms.mddapi.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.exception.DuplicateEntryException;
import com.openclassrooms.mddapi.exception.NoEntryFoundException;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/* Service for User treatment. Handle get and create user actions */
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Save a user in database
     * 
     * @param user The User entity to save
     * @return The User entity saved
     */
    public User save(final User user) {
        if (userRepository.existsByEmail(user.getEmail()) || userRepository.existsByUserName(user.getUserName())) {
            throw new DuplicateEntryException("A user already exist with this email address or username");
        } else {
            log.info("User saved");
            return userRepository.save(user);
        }
    }

    /**
     * Find a user by its id
     * 
     * @param id The id of the user to find
     * @return The User entity found
     */
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoEntryFoundException("The user does not exist"));
    }

    /**
     * Update a user
     * 
     * @param id      The id of the user to update
     * @param userDto The UserDto with datas to update
     * @return The User entity updated
     */
    public User update(final Long id, final UserDto userDto) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new NoEntryFoundException("The user does not exist"));

        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setUserName(userDto.getUserName());
        userToUpdate.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(userToUpdate);
    }

    /**
     * Adds a given post to the feeds of all users who are subscribed to the topic
     * of the post.
     *
     * @param post the post to be added to the feeds of subscribed users
     */
    public void addPostToFeeds(final Post post) {
        List<User> users = userRepository.findBySubscriptionsContaining(post.getTopic());
        for (User user : users) {
            List<Post> modifiableFeed = new ArrayList<>(user.getFeed());
            modifiableFeed.add(post);
            user.setFeed(modifiableFeed);
            userRepository.save(user);
        }
        log.info("Post added to feeds of subscribed users");
    }

    /**
     * Get all user feed posts, sortBy column, asc/desc.
     * 
     * @param user   The user to get feed Posts
     * @param sortBy Name of column to sortBy (default = date)
     * @param asc    order for sortBy (default = true, so ASC)
     * @return the feed posts list, ordered
     */
    public List<Post> getFeedSorted(final User user, final String sortBy, final boolean asc) {
        final List<Post> posts = user.getFeed();
        if (sortBy != null && !sortBy.isEmpty()) {
            Comparator<Post> comparator = null;
            switch (sortBy.toLowerCase()) {
                case "date":
                    comparator = Comparator.comparing(post -> post.getCreatedAt());
                    break;
                case "username":
                    comparator = Comparator.comparing(post -> post.getUser().getUserName());
                    break;
                case "title":
                    comparator = Comparator.comparing(post -> post.getTitle());
                    break;
                default:
                    comparator = Comparator.comparing(post -> post.getCreatedAt());
                    break;
            }
            posts.sort(comparator);

            if (!asc) {
                Collections.reverse(posts); // Reverse list to DESC order
            }
        }
        return posts;
    }

    /**
     * Verify if user is the same has the user authenticate
     * 
     * @param id The user we want to test
     * @return true if users are the same
     */
    public boolean isCurrentUserAuthorized(final User user) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return Objects.equals(userDetails.getUsername(), user.getEmail()) ||
                Objects.equals(userDetails.getUsername(), user.getUserName());
    }

}
