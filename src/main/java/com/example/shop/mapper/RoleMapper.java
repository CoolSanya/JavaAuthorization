package com.example.shop.mapper;

import com.example.shop.dto.roles.AddRoleDto;
import com.example.shop.dto.roles.RoleItemDto;
import com.example.shop.entities.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(source = "name", target = "name")
    RoleEntity AddRoleDtoToRole(AddRoleDto dto);
    RoleItemDto RoleToRoleItemDto(RoleEntity role);
    List<RoleItemDto> RolesToRoleItems(List<RoleEntity> roles);
}
