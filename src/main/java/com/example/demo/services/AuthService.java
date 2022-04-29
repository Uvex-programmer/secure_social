package com.example.demo.services;

import com.example.demo.models.SuperAdmin;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.SuperAdminRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.services.implementation.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final SuperAdminRepository superAdminRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(SuperAdminRepository superAdminRepository, GroupRepository groupRepository, UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.superAdminRepository = superAdminRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public Object setAuthentication(String username, String password) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return jwtUtils.generateJwtCookie(userDetails);
    }

    public boolean checkGroupAccess(String groupId, String username){
        var user = userRepository.findByUsername(username);
        var group = groupRepository.findByGroupIdAndUserId(groupId, user.get().getId());

        return group.isPresent();
    }

    public boolean checkIfSuperAdmin(String username) {
        Optional<SuperAdmin> temp = superAdminRepository.findByUsername(username);
        return temp.isPresent();
    }
}
