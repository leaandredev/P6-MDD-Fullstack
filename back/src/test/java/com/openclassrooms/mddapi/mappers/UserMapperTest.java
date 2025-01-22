package com.openclassrooms.mddapi.mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.User;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private UserDto mockUserDto;
    private User mockUser;

    @BeforeEach
    public void beforeEach() {
        LocalDateTime now = LocalDateTime.now();

        mockUser = User.builder()
                .id(1L)
                .email("test@email.com")
                .userName("Deborah1234")
                .password("password1234!")
                .createdAt(now)
                .updatedAt(now)
                .build();
        mockUserDto = new UserDto();
        mockUserDto.setId(1L);
        mockUserDto.setEmail("test@email.com");
        mockUserDto.setUserName("Deborah1234");
        mockUserDto.setPassword("password1234!");
        mockUserDto.setCreatedAt(now);
        mockUserDto.setUpdatedAt(now);
    }

    @Test
    public void testToEntity() {
        // Act
        User user = userMapper.toEntity(mockUserDto);

        // Assert
        assertThat(user).isNotNull();
        assertThat(user).isEqualTo(mockUser);
    }

    @Test
    public void testNullDtoToEntity() {
        // Act
        User user = userMapper.toEntity((UserDto) null);

        // Assert
        assertThat(user).isNull();
    }

    @Test
    public void testToDto() {
        // Act
        UserDto userDto = userMapper.toDto(mockUser);

        // Assert
        assertThat(userDto).isNotNull();
        assertThat(userDto).isEqualTo(mockUserDto);
    }

    @Test
    public void testNullEntityToDto() {
        // Act
        UserDto userDto = userMapper.toDto((User) null);

        // Assert
        assertThat(userDto).isNull();
    }

    @Test
    public void testToEntityList() {
        // Arrange
        List<UserDto> mockUserDtoList = Arrays.asList(mockUserDto, mockUserDto);

        // Act
        List<User> userList = userMapper.toEntity(mockUserDtoList);

        // Assert
        assertThat(userList).isNotNull();
        assertThat(userList).hasSize(2);
    }

    @Test
    public void testNullUserDtoListToEntityList() {
        // Arrange
        List<UserDto> mockUserDtoList = null;

        // Act
        List<User> userList = userMapper.toEntity(mockUserDtoList);

        // Assert
        assertThat(userList).isNull();
    }

    @Test
    public void testToDtoList() {
        // Arrange
        List<User> mockUserList = Arrays.asList(mockUser, mockUser);

        // Act
        List<UserDto> userDtoList = userMapper.toDto(mockUserList);

        // Assert
        assertThat(userDtoList).isNotNull();
        assertThat(userDtoList).hasSize(2);
    }

    @Test
    public void testNullUserListToDtoList() {
        // Arrange
        List<User> mockUserList = null;

        // Act
        List<UserDto> userDtoList = userMapper.toDto(mockUserList);

        // Assert
        assertThat(userDtoList).isNull();
    }

}
