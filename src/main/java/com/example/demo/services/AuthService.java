package com.example.demo.services;

import com.example.demo.models.ERole;
import com.example.demo.models.SuperAdmin;
import com.example.demo.models.User;
import com.example.demo.payload.responses.AuthenticationResponseDto;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.SuperAdminRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.AuthenticationFacade;
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
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public AuthService(SuperAdminRepository superAdminRepository, GroupRepository groupRepository, UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils, AuthenticationFacade authenticationFacade) {
        this.superAdminRepository = superAdminRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.authenticationFacade = authenticationFacade;
    }

    public Object setAuthentication(String username, String password) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return jwtUtils.generateJwtCookie(userDetails);
    }

    public AuthenticationResponseDto checkGroupAccess(String groupId, String username){
        var user = userRepository.findByUsername(username);
                if(checkIfSuperAdmin()) return new AuthenticationResponseDto()
                .setAuthenticated(true)
                .setRole("SuperAdmin");

        var group = groupRepository.findByGroupIdAndUserId(groupId, user.get().getId());
        if(group.isEmpty()){
            return new AuthenticationResponseDto()
                    .setAuthenticated(false)
                    .setRole("");
        }

        var checkIfModerator = group.get().getModerators().stream()
                .anyMatch(member -> member.getUsername().equals(user.get().getUsername()));

        if(checkIfModerator) return new AuthenticationResponseDto()
        .setAuthenticated(true)
        .setRole("Moderator");

        var checkIfAdmin = group.get().getAdmins().stream()
                .anyMatch(member -> member.getUsername().equals(user.get().getUsername()));

        if(checkIfAdmin) return new AuthenticationResponseDto()
                .setAuthenticated(true)
                .setRole("Admin");
        
        return new AuthenticationResponseDto()
                .setAuthenticated(true)
                .setRole("Member");

    }

    public boolean checkIfSuperAdmin() {
        String username = authenticationFacade.getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);
        var admin = user.get().getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_ADMIN));
        return admin;
    }
}
