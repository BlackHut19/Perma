package com.example.perma.group;

import com.example.perma.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_group")
public class Group {

    @Id
    @GeneratedValue
    private Integer id;
    private String groupname;

    @OneToMany(mappedBy = "group")
    private List<User> users;





}
