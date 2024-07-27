package com.atmosware.belatrix.managmentService.core.filters;

import com.atmosware.belatrix.managmentService.business.abstracts.UserService;
import com.atmosware.belatrix.managmentService.core.responses.SecurityResponse;
import com.atmosware.belatrix.managmentService.core.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwtHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (jwtHeader != null && jwtHeader.startsWith("Bearer ")) {
                String jwt = jwtHeader.substring(7);
                String username = jwtService.extractUser(jwt);

                if (username != null) {
                    UserDetails user = userService.loadUserByUsername(username);

                    if (jwtService.validateToken(jwt, user)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        authenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    }
                }
            }
            filterChain.doFilter(request, response);
        }
        catch (Exception ex){
            SecurityResponse securityResponse= new SecurityResponse("Authentication Error",
                    "Invalid token or token expired",
                    "401",
                    "http://mydomain.com/exceptions/authentication"
            );
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(objectMapper.writeValueAsString(securityResponse));
        }
    }
}