package com.movie.movieapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user")
@Data
@NoArgsConstructor
public class User  implements Serializable {

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
    @Column(name = "role_fk")
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id"))
    private Collection<Role> roles;

    public User(String name,String username,String password, Collection<Role> roles)
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
