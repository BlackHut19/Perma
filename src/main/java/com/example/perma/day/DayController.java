package com.example.perma.day;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/day")
@RequiredArgsConstructor
public class DayController {

    private final DayService dayService;

    @GetMapping("/all")
    public ResponseEntity<List<Day>> getDays(
            Authentication authentication,
            @RequestParam String groupname
    ){
        return ResponseEntity.ok(dayService.getDays(authentication.getName(), groupname));
    }

    @GetMapping("/add")
    public ResponseEntity<Day> addDay(
            Authentication authentication,
            @RequestParam String groupname,
            @RequestParam String dayname
    ){
        return ResponseEntity.ok(dayService.addDay(authentication.getName(), groupname, dayname));
    }



    @DeleteMapping("/delete")
    public ResponseEntity<Day> deleteDay(
            Authentication authentication,
            @RequestParam String groupname,
            @RequestParam String dayname
    ){
        return ResponseEntity.ok(dayService.deleteDay(authentication.getName(), groupname, dayname));
    }

}
