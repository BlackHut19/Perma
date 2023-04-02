package com.example.perma.security.user;

import com.example.perma.group.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/createGroup")
    public ResponseEntity<Group> createGroup(
            Authentication authentication,
            @RequestParam String groupname
    ) {
        return ResponseEntity.ok(userService.createGroup(authentication.getName(), groupname));
    }

    @GetMapping("/joinGroup")
    public ResponseEntity<Group> joinGroup(
            Authentication authentication,
            @RequestParam String groupname
    ) {
        return ResponseEntity.ok(userService.requestGroup(authentication.getName(), groupname));
    }


}
