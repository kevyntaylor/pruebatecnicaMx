package com.microservicios.pruebatecnica.controller;

import com.microservicios.pruebatecnica.domain.UserRequest;
import com.microservicios.pruebatecnica.service.DomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrchestratorControllerTest {

    @Mock
    private DomainService domainService;

    private OrchestratorController orchestratorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        orchestratorController = new OrchestratorController(domainService);
    }

    @Test
    void createUser_ValidUserRequest_UserCreatedSuccessfully() {
        // Arrange
        UserRequest userRequest = new UserRequest("John Doe", "john@example.com", 30, "123 Street, City");

        // Act
        ResponseEntity<String> response = orchestratorController.createUser(userRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario creado correctamente", response.getBody());
        verify(domainService).createUser(userRequest);
    }

    @Test
    void createUser_InvalidUserRequest_InvalidDataBadRequest() {
        // Arrange
        UserRequest userRequest = new UserRequest("", "john@example.com", 30, "123 Street, City");

        // Act
        ResponseEntity<String> response = orchestratorController.createUser(userRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Datos inválidos", response.getBody());
        verifyNoInteractions(domainService);
    }

    @Test
    void createUser_UnauthorizedUserRequest_Unauthorized() {
        // Arrange
        UserRequest userRequest = new UserRequest("John Doe", "john@example.com", 17, "123 Street, City");

        // Act
        ResponseEntity<String> response = orchestratorController.createUser(userRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("No autorizado", response.getBody());
        verifyNoInteractions(domainService);
    }

    @Test
    void createUser_ExceptionThrown_InternalServerError() {
        // Arrange
        UserRequest userRequest = new UserRequest("John Doe", "john@example.com", 30, "123 Street, City");
        doThrow(new RuntimeException()).when(domainService).createUser(userRequest);

        // Act
        ResponseEntity<String> response = orchestratorController.createUser(userRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error interno del servidor", response.getBody());
        verify(domainService).createUser(userRequest);
    }

    @Test
    void validateUserData_ValidUserData_ReturnsTrue() {
        // Arrange
        UserRequest userRequest = new UserRequest("John Doe", "john@example.com", 30, "123 Street, City");

        // Act
        boolean isValid = orchestratorController.validateUserData(userRequest);

        // Assert
        assertEquals(true, isValid);
    }

    @Test
    void validateUserData_InvalidUserData_ReturnsFalse() {
        // Arrange
        UserRequest userRequest = new UserRequest("", "john@example.com", 30, "");

        // Act
        boolean isValid = orchestratorController.validateUserData(userRequest);

        // Assert
        assertEquals(false, isValid);
    }
}
