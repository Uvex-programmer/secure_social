package com.example.demo.controllers;

import com.example.demo.models.Group;
import com.example.demo.models.User;
import com.example.demo.payload.requests.CreateGroupRequest;
import com.example.demo.payload.responses.MessageResponse;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/createGroup")
    public ResponseEntity<?> createGroup(@Valid @RequestBody CreateGroupRequest group, Authentication authentication){
        Group newGroup = new Group(group.getName(), group.isPrivate());
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        newGroup.addAdmin(user.get());
        groupRepository.save(newGroup);
        return ResponseEntity.ok(new MessageResponse("You have created a group called: " + newGroup.getName()));
    }
    /*@GetMapping
    public ResponseEntity<?> getAllNonePrivateGroups(){
       // return ResponseEntity.ok().body(groupRepository.findAll())
    }*/
}
