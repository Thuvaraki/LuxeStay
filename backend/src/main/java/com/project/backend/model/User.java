package com.project.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @JoinTable(name="user_roles",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "userId"),
             inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "roleId"))
    @ManyToMany(fetch= FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private Collection<Role> roles = new HashSet<>();
//    fetch = FetchType.EAGER: Specifies that when a User entity is loaded, its associated Role entities should be eagerly fetched from the database
//    CascadeType.PERSIST: Persists(saves) the Role entities when a User entity is persisted.
//    CascadeType.MERGE: Updates the Role entities when a User entity is merged (updated).
//    CascadeType.DETACH: Detaches (removes association) of Role entities when a User entity is detached from the persistence context.
}
