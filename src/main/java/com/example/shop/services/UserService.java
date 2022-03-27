package com.example.shop.services;

import com.example.shop.entities.UserEntity;
import com.example.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;


@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByEmail(username);
        if(user==null)
            throw new UsernameNotFoundException("Email "+ username +" not found");
        return new org.springframework
                .security.core.userdetails.User(user.getEmail(),
                user.getPassword(), getAuthorities(user));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(UserEntity user) {
        String [] userRoles = user.getRoles().stream()
                .map((role) -> role.getName()).toArray(String []:: new);
        Collection<GrantedAuthority> authorityCollections =
                AuthorityUtils.createAuthorityList(userRoles);
        return authorityCollections;
    }
}

//@RestController
//@RequestMapping(path = "api/public")
//@RequiredArgsConstructor
//public class AuthApi {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtTokenUtil jwtTokenUtil;
//    private final UserRepository userRepository;
//    private final UserMapper userMapper;
//
//    @PostMapping("login")
//    public ResponseEntity<UserView> login(@RequestBody @Valid AuthRequest request) {
//        try {
//            Authentication authenticate = authenticationManager
//                    .authenticate(new UsernamePasswordAuthenticationToken(
//                            request.getUsername(),
//                            request.getPassword()));
//
//            User user = (User) authenticate.getPrincipal();
//            com.example.springboot.entities.User dbUser = userRepository
//                    .findByUsername(user.getUsername());
//            UserView userView = userMapper.UserToUserView(dbUser);// new UserView();
//            //userView.setUsername(user.getUsername());
//            String token = jwtTokenUtil.generateAccessToken(dbUser);
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.AUTHORIZATION, token)
//                    .body(userView);
//        } catch (BadCredentialsException ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }
//
//}
