package com.example.demo.controllers;

import com.example.demo.dto.UserDto;
import com.example.demo.mapper.Mapper;
import com.example.demo.models.ERole;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.payload.requests.LoginRequest;
import com.example.demo.payload.requests.SignupRequest;
import com.example.demo.payload.responses.AuthenticationResponseDto;
import com.example.demo.payload.responses.MessageResponse;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.AuthenticationFacade;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.services.AuthService;
import com.example.demo.services.UserService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationFacade authenticationFacade;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthService authService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    @Autowired
    Mapper mapper;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){

        var jwtCookie = authService.setAuthentication(loginRequest.getUsername(), loginRequest.getPassword());
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        UserDto userDto = mapper.mapUserToDto(user.get());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(userDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);

        var jwtCookie = authService.setAuthentication(signUpRequest.getUsername(), signUpRequest.getPassword());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @GetMapping("/whoami")
    @ResponseBody
    public ResponseEntity<?> rememberMe(@CookieValue("sessionId") String sessionId) {
        if(!StringUtils.hasText(sessionId))
            return ResponseEntity.ok().body(new MessageResponse("No user logged in"));

        var username = jwtUtils.getUsernameFromJwtToken(sessionId);

        if(!StringUtils.hasText(username))
            return ResponseEntity.ok().body(new MessageResponse("Bad cookie"));

        var user = userService.findByUsername(username);
        return ResponseEntity.ok().body(mapper.mapUserToDto(user.get()));
    }

    @PostMapping("/authenticate")
    @ResponseBody
    public ResponseEntity<?> authenticate(@RequestBody String groupId) {

        var username = authenticationFacade.getAuthentication().getName();
        if(username.equals("anonymousUser"))
            return ResponseEntity.ok().body(new MessageResponse("NO USER SIGNED IN!"));

        AuthenticationResponseDto access = authService.checkGroupAccess(groupId, username);

        if(!access.isAuthenticated())
            return ResponseEntity.ok().body(new MessageResponse("YOU HAVE NO PERMISSION TO ENTER THIS GROUP"));

        return ResponseEntity.ok().body(access);
    }
}
