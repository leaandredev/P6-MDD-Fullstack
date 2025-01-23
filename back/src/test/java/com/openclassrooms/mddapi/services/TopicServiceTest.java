package com.openclassrooms.mddapi.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

@ExtendWith(MockitoExtension.class)
public class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicService topicService;

    private Topic mockTopic;
    private TopicDto mockTopicDto;

    @BeforeEach
    public void beforeEach() {
        mockTopic = Topic.builder()
                .id(1L)
                .title("Test Topic")
                .description("This is a test topic")
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

}
