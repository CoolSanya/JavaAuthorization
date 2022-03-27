package com.example.shop.loader;

import com.example.shop.constants.Roles;
import com.example.shop.entities.RoleEntity;
import com.example.shop.entities.UserEntity;
import com.example.shop.repositories.RoleRepository;
import com.example.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public DatabaseLoader(UserRepository userRepository,
                          RoleRepository roleRepository) {
        this.roleRepository=roleRepository;
        this.userRepository=userRepository;
    }

    @Override
    public  void run(String... args) throws  Exception{

        if(this.roleRepository.count()==0)
        {
            this.roleRepository.save(new RoleEntity(Roles.Admin));
            this.roleRepository.save(new RoleEntity(Roles.User));
        }
        if(this.userRepository.count()==0)
        {
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            UserEntity user = new UserEntity();
            user.setName("semen");
            user.setEmail("semen@gmail.com");
            user.setPhone("0987654321");
            user.setPassword(encoder.encode("123456"));
            user.setRoles(Arrays.asList(
                    roleRepository.findByName(Roles.Admin)));
            this.userRepository.save(user);
        }
    }
}