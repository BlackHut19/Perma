package com.example.perma.group;
import com.example.perma.security.user.User;
import com.example.perma.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserService userService;

    public void save(Group group){
        groupRepository.save(group);
    }


    public Group loadGroupByGroupname(String groupname) {
        return groupRepository
                .findByGroupname(groupname)
                .orElseThrow(() ->
                        new IllegalStateException(String.format("Groupname %s not found.", groupname)));
    }

    public List<User> getRequests(String username, String groupname) {
        User user = userService.loadUserByUsername(username);
        Group group = loadGroupByGroupname(groupname);
        if (user.getGroup() != group) throw new IllegalStateException(String.format("User not in %s group", groupname));
        return userService.getUsersWithRequestedGroupId(group.getId());

    }

    public User acceptRequest(String username, String groupname, String requesterUsername) {
        User user = userService.loadUserByUsername(username);
        Group group = loadGroupByGroupname(groupname);
        if (user.getGroup() != group) throw new IllegalStateException(String.format("User not in %s group", groupname));

        User requesterUser = userService.loadUserByUsername(requesterUsername);
        if (requesterUser.getRequestedGroup() != group) throw new IllegalStateException(String.format("User not in %s group", groupname));
        return userService.requestAccepted(requesterUser, group);





    }
}
