package com.example.perma.repositories;

import com.example.perma.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

  List<User> findUsersByRequestedGroupId(Integer id);

  User findUserById(Integer id);


  List<User> findUsersByGroupId(Integer id);
}
