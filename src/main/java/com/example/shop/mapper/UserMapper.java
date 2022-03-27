package com.example.shop.mapper;

import com.example.shop.dto.roles.AddRoleDto;
import com.example.shop.dto.roles.RoleItemDto;
import com.example.shop.dto.roles.UserDto;
import com.example.shop.entities.RoleEntity;
import com.example.shop.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "name", target = "name")
    UserEntity UserDtoToUser(UserDto dto);
    UserDto UserEnyityToUserDto(UserEntity user);
}
