package com.example.perma.controllers;

import com.example.perma.models.Day;
import com.example.perma.models.Group;
import com.example.perma.models.User;
import com.example.perma.services.MultiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MultiController {

    private final MultiService multiService;

    @GetMapping("/group/request/all")
    public ResponseEntity<List<User>> getGroupRequests(
            Authentication authentication,
            @RequestParam Integer groupId
    ){
        return ResponseEntity.ok(multiService.getRequests(authentication.getName(), groupId));
    }

    @GetMapping("/group/request/accept")
    public ResponseEntity<User> acceptGroupRequest(
            Authentication authentication,
            @RequestParam Integer groupId,
            @RequestParam Integer userId
    ){
        return ResponseEntity.ok(multiService.acceptRequest(authentication.getName(), groupId, userId));
    }

    @GetMapping("/group/day/all")
    public ResponseEntity<List<Day>> getDays(
            Authentication authentication,
            @RequestParam Integer groupId
    ){
        return ResponseEntity.ok(multiService.getDays(authentication.getName(), groupId));
    }

    @GetMapping("/group/day/add")
    public ResponseEntity<Day> addDay(
            Authentication authentication,
            @RequestParam Integer groupId,
            @RequestParam String dayname
    ){
        return ResponseEntity.ok(multiService.addDay(authentication.getName(), groupId, dayname));
    }



    @DeleteMapping("/group/day/delete")
    public ResponseEntity<Day> deleteDay(
            Authentication authentication,
            @RequestParam Integer dayId
    ){
        return ResponseEntity.ok(multiService.deleteDay(authentication.getName(), dayId));
    }



    @GetMapping("/group/add")
    public ResponseEntity<Group> createGroup(
            Authentication authentication,
            @RequestParam String groupname
    ) {
        return ResponseEntity.ok(multiService.createGroup(authentication.getName(), groupname));
    }

    @GetMapping("/group/join")
    public ResponseEntity<Group> joinGroup(
            Authentication authentication,
            @RequestParam Integer groupId
    ) {
        return ResponseEntity.ok(multiService.requestGroup(authentication.getName(), groupId));
    }






}
