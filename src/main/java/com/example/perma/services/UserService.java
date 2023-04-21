package com.example.perma.services;

import com.example.perma.models.Group;
import com.example.perma.models.User;
import com.example.perma.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

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



    public List<User> getUsersWithRequestedGroupId(Integer id){
        return userRepository.findUsersByRequestedGroupId(id);
    }

    public List<User> getUsersWithGroupId(Integer id) {
        return userRepository.findUsersByGroupId(id);
    }


    public User requestAccepted(User user, Group group) {
        user.setRequestedGroup(null);
        user.setGroup(group);
        return user;
    }

    public User loadUserById(Integer id) {
        return userRepository.findUserById(id);
    }
}
