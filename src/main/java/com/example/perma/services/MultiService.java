package com.example.perma.services;

import com.example.perma.models.Day;
import com.example.perma.models.Group;
import com.example.perma.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MultiService {

    private final UserService userService;
    private final GroupService groupService;
    private final DayService dayService;



    public Group createGroup(String username, String groupname) {
        User user = userService.loadUserByUsername(username);
        if (user.getGroup() != null) throw new IllegalStateException("User already in group");
        Group group = Group.builder()
                .groupname(groupname)
                .build();
        groupService.save(group);
        user.setGroup(group);
        return group;
    }


    public Group requestGroup(String username, Integer groupId) {
        User user = userService.loadUserByUsername(username);
        Group group = groupService.loadGroupByGroupId(groupId);
        if (user.getGroup() != null) throw new IllegalStateException(String.format("User not in %s group", group.getGroupname()));
        user.setRequestedGroup(group);
        return group;

    }

    public List<User> getRequests(String username, Integer groupId) {
        User user = userService.loadUserByUsername(username);
        Group group = groupService.loadGroupByGroupId(groupId);
        if (user.getGroup() != group) throw new IllegalStateException(String.format("User not in %s group", group.getGroupname()));
        return userService.getUsersWithRequestedGroupId(groupId);
    }

    public List<User> getUsers(String username, Integer groupId) {
        User user = userService.loadUserByUsername(username);
        Group group = groupService.loadGroupByGroupId(groupId);
        if (user.getGroup() != group) throw new IllegalStateException(String.format("User not in %s group", group.getGroupname()));
        return userService.getUsersWithGroupId(groupId);
    }


    public User acceptRequest(String username, Integer groupId, Integer userId) {
        User user = userService.loadUserByUsername(username);
        Group group = groupService.loadGroupByGroupId(groupId);
        if (user.getGroup() != group) throw new IllegalStateException(String.format("User not in %s group", group.getGroupname()));
        User requesterUser = userService.loadUserById(userId);
        if (requesterUser.getRequestedGroup() != group) throw new IllegalStateException(String.format("User %s not requested to join %s group", requesterUser.getUsername() ,group.getGroupname()));
        return userService.requestAccepted(requesterUser, group);
    }

    public List<Day> getDays(String username, Integer groupId) {
        User user = userService.loadUserByUsername(username);
        Group group = groupService.loadGroupByGroupId(groupId);
        if (user.getGroup() != group) throw new IllegalStateException(String.format("User not in %s group", group.getGroupname()));
        return dayService.loadDaysByGroupId(group.getId());
    }


    public Day addDay(String username, Integer groupId, String dayname) {
        User user = userService.loadUserByUsername(username);
        Group group = groupService.loadGroupByGroupId(groupId);
        if (user.getGroup() != group) throw new IllegalStateException(String.format("User not in %s group", group.getGroupname()));
        Day day = Day.builder()
                .dayname(dayname)
                .group(group)
                .build();
        dayService.save(day);
        return day;
    }


    public Day deleteDay(String username, Integer dayId) {
        User user = userService.loadUserByUsername(username);
        Day day = dayService.loadDayByDayId(dayId);
        if (user.getGroup() != day.getGroup()) throw new IllegalStateException(String.format("User not in %s group", day.getGroup().getGroupname()));
        dayService.delete(day);
        return day;
    }

    public Day joinDay(String username, Integer dayId) {
        User user = userService.loadUserByUsername(username);
        Day day = dayService.loadDayByDayId(dayId);
        if (user.getGroup() != day.getGroup()) throw new IllegalStateException(String.format("User not in %s group", day.getGroup().getGroupname()));
        user.getDays().add(day);
        return day;
    }

    public Day unJoinDay(String username, Integer dayId) {
        User user = userService.loadUserByUsername(username);
        Day day = dayService.loadDayByDayId(dayId);
        if (user.getGroup() != day.getGroup()) throw new IllegalStateException(String.format("User not in %s group", day.getGroup().getGroupname()));
        user.getDays().remove(day);
        return day;
    }







}
