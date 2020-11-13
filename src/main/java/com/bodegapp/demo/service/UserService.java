package com.bodegapp.demo.service;

import com.bodegapp.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> deleteUser(Long userId);
    User updateUser(Long userId, User userRequest);
    User createUser(User user);
    User getUserById(Long userId);
    Page<User> getAllUsers(Pageable pageable);
    User login(User user);  
}
