package com.example.perma.security.user;

import com.example.perma.group.Group;
import com.example.perma.group.GroupService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final GroupService groupService;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s not found.", email)));
    }

    public User signUpUser(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()) throw new IllegalStateException("Email already taken");
        userRepository.save(user);
        return user;
    }

    public void enableUser(String email) {
        User user = loadUserByUsername(email);
        user.setIsEnabled(true);
    }

    @Transactional
    public Group createGroup(String username, String groupname){
        User user = loadUserByUsername(username);
        if (user.getGroup() != null) throw new IllegalStateException("User already in group");
        Group group = Group.builder()
                .groupname(groupname)
                .build();
        groupService.save(group);
        user.setGroup(group);
        return group;
    }

    public Group requestGroup(String username, String groupname){
        User user = loadUserByUsername(username);
        if (user.getGroup() != null) throw new IllegalStateException("User already in group");
        Group group = groupService.loadGroupByGroupname(groupname);
        user.setRequestedGroup(group);
        return group;
    }

    public List<User> getUsersWithRequestedGroupId(Integer id){
        return userRepository.findUsersByRequestedGroupId(id);
    }


    public User requestAccepted(User user, Group group) {
        user.setRequestedGroup(null);
        user.setGroup(group);
        return user;
    }
}
