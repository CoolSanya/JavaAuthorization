package com.example.shop.web;

import com.example.shop.configuration.security.JwtTokenUtil;
import com.example.shop.dto.roles.AddRoleDto;
import com.example.shop.dto.roles.RoleItemDto;
import com.example.shop.dto.roles.UserDto;
import com.example.shop.entities.RoleEntity;
import com.example.shop.entities.UserEntity;
import com.example.shop.mapper.RoleMapper;
import com.example.shop.mapper.UserMapper;
import com.example.shop.repositories.RoleRepository;
import com.example.shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/")
    public List<RoleItemDto> index() {
        return roleMapper.RolesToRoleItems(roleRepository.findAll());
    }

    @PostMapping("/create")
    public RoleItemDto create(@RequestBody AddRoleDto dto) {
        RoleEntity role = roleMapper.AddRoleDtoToRole(dto);
        roleRepository.save(role);
        return roleMapper.RoleToRoleItemDto(role);
    }

    @PostMapping("login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid UserDto request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));

            UserEntity user = (UserEntity) authenticate.getPrincipal();
            com.example.shop.entities.UserEntity dbUser = userRepository
                    .findByEmail(user.getEmail());
            UserDto userDto = userMapper.UserEnyityToUserDto(dbUser);// new UserView();
            //userView.setUsername(user.getUsername());
            String token = jwtTokenUtil.generateAccessToken(dbUser);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body(userDto);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
