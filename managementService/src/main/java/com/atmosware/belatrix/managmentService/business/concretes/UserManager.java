package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.managmentService.business.abstracts.UserService;
import com.atmosware.belatrix.managmentService.business.dto.dtos.RegisterUserDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.UpdateUserRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.GetByIdUserResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.UpdateUserResponse;
import com.atmosware.belatrix.managmentService.business.mappers.UserMapper;
import com.atmosware.belatrix.managmentService.business.rules.UserBusinessRules;
import com.atmosware.belatrix.managmentService.core.service.JwtService;
import com.atmosware.belatrix.managmentService.dataAccess.UserRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import com.atmosware.belatrix.managmentService.entities.concretes.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class UserManager implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserBusinessRules userBusinessRules;
    private final JwtService jwtService;
    @Override
    public void addOrganization(RegisterUserDto registerUserDto, Organization organization) {
        this.userBusinessRules.userCanNotBeDuplicated(registerUserDto.email());

        User user = this.userMapper.toUser(registerUserDto);

        String encodedPassword =this.passwordEncoder.encode(registerUserDto.password());
        user.setPassword(encodedPassword);

        user.setOrganization(organization);

        this.userRepository.save(user);
    }

    @Override
    public GetByIdUserResponse getById(UUID id) {
        this.userBusinessRules.userShouldBeExists(id);

        User user = this.userRepository.findById(id).get();

        return this.userMapper.toGetByIdUserResponse(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findByEmail(email);
    }
    @Override
    public User findByEmail(String email) {
        Optional<User> userOptional = this.userRepository.findByEmail(email);
        this.userBusinessRules.userShouldBeExists(userOptional);
        return userOptional.get();
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest, @NonNull HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        Claims aaa=jwtService.getClaims(token);

        Object bbb = aaa.get("id");

        User user = this.userRepository.findById(UUID.fromString(bbb.toString())).get();

        user.setPassword(this.passwordEncoder.encode(updateUserRequest.password()));

        return this.userMapper.toUpdateUserResponse(this.userRepository.save(user));
    }
}
