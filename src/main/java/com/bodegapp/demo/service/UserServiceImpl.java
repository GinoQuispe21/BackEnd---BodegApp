package com.bodegapp.demo.service;

import com.bodegapp.demo.exception.ResourceNotFoundException;
import com.bodegapp.demo.model.User;
import com.bodegapp.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    @Override
    public User updateUser(Long userId, User userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        user.setUsername(userRequest.getUsername());
        user.setDni(userRequest.getDni());
        user.setLastname(userRequest.getLastname());
        user.setAddress(userRequest.getAddress());
        user.setDistrict(userRequest.getDistrict());
        user.setCountry(userRequest.getCountry());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setBirthdate(userRequest.getBirthdate());
        user.setPassword(userRequest.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User createUser(User user) { return userRepository.save(user); }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Player", "Id", userId));
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User login(User userRequest) {
        User myUser = userRepository.findByUsername(userRequest.getUsername());
        if(myUser == null){
            return userRequest;
        }
            if(userRequest.getPassword().equals(myUser.getPassword())){
            return myUser;
        }
        return userRequest;
    }
}
