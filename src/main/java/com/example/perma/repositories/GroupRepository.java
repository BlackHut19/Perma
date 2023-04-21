package com.example.perma.repositories;

import com.example.perma.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    Optional<Group> findByGroupname(String groupname);
    Optional<Group> findGroupById(Integer Id);
}
