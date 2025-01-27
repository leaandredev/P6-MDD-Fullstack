package com.openclassrooms.mddapi.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import java.util.Optional;
import com.openclassrooms.mddapi.exception.NoEntryFoundException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TopicService topicService;

    private Topic mockTopic;
    private TopicDto mockTopicDto;

    private User mockUser;

    @BeforeEach
    public void beforeEach() {
        mockTopic = Topic.builder()
                .id(1L)
                .title("Test Topic")
                .description("This is a test topic")
                .build();

        mockUser = User.builder()
                .id(1L)
                .userName("test")
                .email("test@email.com")
                .password("password")
                .subscriptions(new ArrayList<>())
                .build();

        mockTopicDto = new TopicDto();
        mockTopicDto.setTitle("Test Topic Updated");
        mockTopicDto.setDescription("This is a test topic updated");
    }

    @Test
    public void testFindAll() {
        // Arrange
        when(topicRepository.findAll()).thenReturn(List.of(mockTopic));

        // Act
        List<Topic> actualTopics = topicService.findAll();

        // Assert
        assertThat(actualTopics).isNotNull();
        assertThat(actualTopics).containsExactly(mockTopic);
        verify(topicRepository).findAll();
    }

    @Test
    public void testSubscribe_UserAndTopicExist() {
        // Arrange
        Long topicId = 1L;
        Long userId = 1L;
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(mockTopic));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        topicService.subscribe(topicId, userId);

        // Assert
        assertThat(mockUser.getSubscriptions()).contains(mockTopic);
        verify(userRepository).save(mockUser);
    }

    @Test
    public void testSubscribe_UserOrTopicNotFound() {
        // Arrange
        Long topicId = 1L;
        Long userId = 1L;
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> topicService.subscribe(topicId, userId))
                .isInstanceOf(NoEntryFoundException.class)
                .hasMessage("User or topic not found");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testSubscribe_UserAlreadySubscribed() {
        // Arrange
        Long topicId = 1L;
        Long userId = 1L;
        mockUser.getSubscriptions().add(mockTopic);
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(mockTopic));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        topicService.subscribe(topicId, userId);

        // Assert
        assertThat(mockUser.getSubscriptions()).contains(mockTopic);
        verify(userRepository, never()).save(mockUser);
    }

    @Test
    public void testUnsubscribe_UserAndTopicExist() {
        // Arrange
        Long topicId = 1L;
        Long userId = 1L;
        mockUser.getSubscriptions().add(mockTopic);
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(mockTopic));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        topicService.unsubscribe(topicId, userId);

        // Assert
        assertThat(mockUser.getSubscriptions()).doesNotContain(mockTopic);
        verify(userRepository).save(mockUser);
    }

    @Test
    public void testUnsubscribe_UserOrTopicNotFound() {
        // Arrange
        Long topicId = 1L;
        Long userId = 1L;
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> topicService.unsubscribe(topicId, userId))
                .isInstanceOf(NoEntryFoundException.class)
                .hasMessage("User or topic not found");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testUnsubscribe_UserNotSubscribed() {
        // Arrange
        Long topicId = 1L;
        Long userId = 1L;
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(mockTopic));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        topicService.unsubscribe(topicId, userId);

        // Assert
        assertThat(mockUser.getSubscriptions()).doesNotContain(mockTopic);
        verify(userRepository, never()).save(mockUser);
    }
}
