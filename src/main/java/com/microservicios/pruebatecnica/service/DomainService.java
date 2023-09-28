package com.microservicios.pruebatecnica.service;

import com.microservicios.pruebatecnica.domain.User;
import com.microservicios.pruebatecnica.domain.UserRequest;
import com.microservicios.pruebatecnica.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class DomainService {

    private final UserRepository userRepository;

    public DomainService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(@Valid UserRequest userRequest) {
        User user = mapUserRequestToUser(userRequest);
        userRepository.save(user);
    }

    private User mapUserRequestToUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setAge(userRequest.getAge());
        user.setAddress(userRequest.getAddress());
        return user;
    }

}