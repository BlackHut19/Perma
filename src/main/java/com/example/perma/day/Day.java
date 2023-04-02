package com.example.perma.day;

import com.example.perma.group.Group;
import com.example.perma.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_day")
public class Day {

    @Id
    @GeneratedValue
    private Integer id;
    private String dayname;

    @ManyToMany
    private Set<User> users;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;


}
