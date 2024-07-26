package com.atmosware.belatrix.managmentService.api.controllers;

import com.atmosware.belatrix.managmentService.business.abstracts.UserService;
import com.atmosware.belatrix.managmentService.business.dto.requests.UpdateUserRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.GetByIdUserResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.UpdateUserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("management-service/api/v1/users")
public class UsersController {
    private final UserService userService;
    @GetMapping("{id}")
    public GetByIdUserResponse getById(@PathVariable UUID id){
        return this.userService.getById(id);
    }

    @PutMapping()
    public UpdateUserResponse update(@Valid @RequestBody UpdateUserRequest updateUserRequest, HttpServletRequest request){
        return this.userService.updateUser(updateUserRequest,request);
    }
}
