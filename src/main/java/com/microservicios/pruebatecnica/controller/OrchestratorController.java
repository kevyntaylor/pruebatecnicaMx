package com.microservicios.pruebatecnica.controller;

import com.microservicios.pruebatecnica.domain.UserRequest;
import com.microservicios.pruebatecnica.service.DomainService;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrchestratorController {

    private final DomainService domainService;

    public OrchestratorController(DomainService domainService) {
        this.domainService = domainService;
    }

    @PostMapping("/users")
    /*@ApiOperation(value = "Crear un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario creado correctamente"),
            @ApiResponse(code = 400, message = "Datos inválidos"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })*/
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequest userRequest) {
        try {
            boolean isValid = validateUserData(userRequest);
            if (!isValid) {
                return ResponseEntity.badRequest().body("Datos inválidos");
            }
            boolean isAuthorized = validateAge(userRequest.getAge());
            if (!isAuthorized) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
            }
            domainService.createUser(userRequest);
            return ResponseEntity.ok("Usuario creado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    boolean validateUserData(UserRequest userRequest) {
        if (StringUtils.isBlank(userRequest.getName())) {
            return false;
        }
        if (StringUtils.isBlank(userRequest.getAddress())) {
            return false;
        }
        return true;
    }

    boolean validateAge(int age) {
        if (age < 18 || age > 99) {
            return false;
        }
        return true;
    }
}