package com.example.client.service;

import com.example.client.dto.UserDTO;
import com.example.client.entity.User;
import com.example.client.mapper.UserMapper;
import com.example.client.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public UserDTO addUser(UserDTO userDTO) {
        User entity = userMapper.toEntity(userDTO);
        User entitySaved = userRepository.save(entity);
        return userMapper.toDto(entitySaved);
    }
}