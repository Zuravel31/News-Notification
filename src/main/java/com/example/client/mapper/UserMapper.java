package com.example.client.mapper;


import com.example.client.dto.UserDTO;
import com.example.client.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);

    User toEntity(UserDTO uSerDTO);
}
