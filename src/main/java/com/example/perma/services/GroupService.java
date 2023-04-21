package com.example.perma.services;

import com.example.perma.models.Group;
import com.example.perma.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public void save(Group group){
        groupRepository.save(group);
    }

    public Group loadGroupByGroupId(Integer groupId) {
        return groupRepository.findGroupById(groupId).orElse(null);
    }

}
