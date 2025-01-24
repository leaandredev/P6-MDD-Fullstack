package com.openclassrooms.mddapi.mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.TopicService;

@Component
@Mapper(componentModel = "spring", uses = { TopicService.class }, imports = { Arrays.class, Collectors.class,
        Topic.class, User.class, Collections.class, Optional.class })
public abstract class UserMapper implements EntityMapper<UserDto, User> {

    @Autowired
    TopicService topicService;

    @Override
    @Mapping(target = "subscriptions", expression = "java(Optional.ofNullable(user.getSubscriptions()).orElseGet(Collections::emptyList).stream().map(t -> t.getId()).collect(Collectors.toList()))")
    public abstract UserDto toDto(User user);

    @Override
    @Mapping(target = "subscriptions", expression = "java(Optional.ofNullable(userDto.getSubscriptions()).orElseGet(Collections::emptyList).stream().map(topic_id -> { Topic topic = this.topicService.findById(topic_id); if (topic != null) { return topic; } return null; }).collect(Collectors.toList()))")
    public abstract User toEntity(UserDto userDto);

}
