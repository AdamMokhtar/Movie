package com.movie.movieapi.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="role")
@Getter
@Setter
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", unique=true)
    private Integer roleId;
    @Column(name = "role_name")
    private String roleName;
    @JsonIgnore
    @Column(name = "user_id")
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}
