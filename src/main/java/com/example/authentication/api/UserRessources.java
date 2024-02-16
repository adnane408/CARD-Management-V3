package com.example.authentication.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.authentication.model.User;
import com.example.authentication.service.UserService;
import com.example.authentication.model.Role;
import com.example.authentication.model.RoleToUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserRessources {
    private final UserService userService;

    @GetMapping("/user")
    public User getUser(@RequestParam String username) {
        return userService.getUser(username);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication) {
        return authentication;
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user.getUsername(), user.getPassword()));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(userService.saveRole(role.getName()));
    }

    @PostMapping("/role/addRU")
    public void addROleToUser(@RequestBody RoleToUser form) {
        userService.addRoletoUser(form.getUsername(), form.getRoleName());
    }

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {


        String authorisationToken = request.getHeader("Authorization");
        if (authorisationToken != null && authorisationToken.startsWith("Bearer ")) {
            String jwt = authorisationToken.substring(7);
            Algorithm algorithm = Algorithm.HMAC256("Mysecret1234");
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
            String username = decodedJWT.getSubject();
            User appUser = userService.loadUserByUsername(username);
            String accessToken = JWT.create()
                    .withSubject(appUser.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + (5 * 60 * 1000)))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("role", appUser.getRole().toString())
                    .sign(algorithm);
            Map<String, String> idToken = new HashMap<>();
            idToken.put("access-token", accessToken);
            idToken.put("refresh-token", jwt);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), idToken);
        }
        }catch (Exception e){
            response.setHeader("error-message",e.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
    @PostMapping("/checkPwd")
    public boolean checkOldPassword(@RequestBody String username,String password){
        return userService.verifyOldPassword(username,password) ;
    }

}

