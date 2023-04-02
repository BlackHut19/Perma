package com.example.perma.group;

import com.example.perma.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/requests")
    public ResponseEntity<List<User>> getGroupRequests(
            Authentication authentication,
            @RequestParam String groupname
    ){
        return ResponseEntity.ok(groupService.getRequests(authentication.getName(), groupname));
    }

    @GetMapping("/accept")
    public ResponseEntity<User> acceptGroupRequest(
            Authentication authentication,
            @RequestParam String groupname,
            @RequestParam String username
    ){
        return ResponseEntity.ok(groupService.acceptRequest(authentication.getName(),groupname,username));
    }


}
