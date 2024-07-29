package com.atmosware.belatrix.managmentService.api.controllers;

import com.atmosware.belatrix.managmentService.business.abstracts.AuthService;
import com.atmosware.belatrix.managmentService.business.dto.dtos.RegisterUserDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.auth.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("management-service/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response, HttpServletRequest request){
        return this.authService.login(loginRequest);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public void register(@Valid @RequestBody RegisterUserDto registerUserDto,HttpServletRequest request){
        this.authService.register(registerUserDto,request);
    }
}
