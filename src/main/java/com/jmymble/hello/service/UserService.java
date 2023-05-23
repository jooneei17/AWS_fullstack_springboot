package com.jmymble.hello.service;

import com.jmymble.hello.model.UserEntity;
import com.jmymble.hello.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity) {
        if(userEntity == null || userEntity.getUsername() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        final String username = userEntity.getUsername();
        if(userRepository.existsByUsername(username)){
            log.warn("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }
        return userRepository.save(userEntity);
    }
    
    //로그인
    public UserEntity getByCredentials(final String username, final String password, PasswordEncoder encoder) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity != null && encoder.matches(password, userEntity.getPassword())) {
            return userEntity;
        }
        return null;
//        return userRepository.findByUsernameAndPassword(username, password);
    }
}
