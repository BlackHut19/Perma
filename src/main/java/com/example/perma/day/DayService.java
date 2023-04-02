package com.example.perma.day;

import com.example.perma.group.Group;
import com.example.perma.group.GroupService;
import com.example.perma.security.user.User;
import com.example.perma.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DayService {

    private final DayRepository dayRepository;

    private final UserService userService;
    private final GroupService groupService;

    private Day loadDayByGroupIdAndDayname(Integer groupId, String dayname) {
        return dayRepository.findDayByGroupIdAndDayname(groupId, dayname);
    }


    public List<Day> getDays(String username, String groupname) {
        User user = userService.loadUserByUsername(username);
        Group group = groupService.loadGroupByGroupname(groupname);
        if (user.getGroup() != group) throw new IllegalStateException(String.format("User not in %s group", groupname));
        return dayRepository.findByGroup_Id(group.getId());
    }

    public Day addDay(String username, String groupname, String dayname) {
        User user = userService.loadUserByUsername(username);
        Group group = groupService.loadGroupByGroupname(groupname);
        if (user.getGroup() != group) throw new IllegalStateException(String.format("User not in %s group", groupname));

        Day day = Day.builder()
                .dayname(dayname)
                .group(group)
                .build();

        dayRepository.save(day);
        return day;

    }

    public Day deleteDay(String username, String groupname, String dayname) {
        User user = userService.loadUserByUsername(username);
        Group group = groupService.loadGroupByGroupname(groupname);
        if (user.getGroup() != group) throw new IllegalStateException(String.format("User not in %s group", groupname));
        Day day = loadDayByGroupIdAndDayname(group.getId(), dayname);
        dayRepository.delete(day);
        return day;

    }
}
