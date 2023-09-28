package com.microservicios.pruebatecnica.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.microservicios.pruebatecnica.domain.User;
import com.microservicios.pruebatecnica.domain.UserRequest;
import com.microservicios.pruebatecnica.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DomainService.class})
@ExtendWith(SpringExtension.class)
class DomainServiceTest {
    @Autowired
    private DomainService domainService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testCreateUser() {
        User user = new User();
        user.setAddress("42 Main St");
        user.setAge(1);
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);

        UserRequest userRequest = new UserRequest();
        userRequest.setAddress("42 Main St");
        userRequest.setAge(1);
        userRequest.setEmail("jane.doe@example.org");
        userRequest.setName("Name");
        domainService.createUser(userRequest);
        verify(userRepository).save(Mockito.<User>any());
    }
}

