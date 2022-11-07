package com.movie.movieapi.entity;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
public class User  implements Serializable {

    @ToString.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique=true)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
//    , referencedColumnName = "role_id"
    @JsonIgnore
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String name,String username,String password, Set<Role> roles)
    {
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(String name,String username,String password)
    {
        this.name = name;
        this.username = username;
        this.password = password;
//        this.roles = new HashSet<>();
//        this.roles.add();
    }


}
