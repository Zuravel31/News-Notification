package com.example.client.service;

import com.example.client.dto.UserDTO;
import com.example.client.entity.User;
import com.example.client.mapper.UserMapper;
import com.example.client.repository.UserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO;

    private User userEntity;

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO();
        userDTO.setName("Test User");

        userEntity = new User();
        userEntity.setId(1L);
        userEntity.setName("Test User");
    }

    @Test
    public void testAddUser() {
        // Arrange
        given(userMapper.toEntity(userDTO)).willReturn(userEntity);
        given(userRepository.save(userEntity)).willReturn(userEntity);
        given(userMapper.toDto(userEntity)).willReturn(userDTO);

        // Act
        UserDTO result = userService.addUser(userDTO);

        // Assert
        assertEquals(userDTO, result);
        verify(userRepository, times(1)).save(userEntity);
    }
}
