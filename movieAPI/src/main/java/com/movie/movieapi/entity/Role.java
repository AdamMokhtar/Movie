package com.movie.movieapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="role")
@Data
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", unique=true)
    private Integer roleId;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "user_id")
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
}
