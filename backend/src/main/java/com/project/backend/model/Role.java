package com.project.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    public void assignRoleToUser(User user){
        user.getRoles().add(this);
        this.getUsers().add(user);
    }

    public void removeUserFromRole(User user){
        user.getRoles().remove(this);
        this.getUsers().remove(user);
    }

//    public void removeAllUsersFromRole() {
//        if (this.getUsers() != null) {
//            this.getUsers().clear(); //this method won't update the roles in each User.
//    }

    public void removeAllUsersFromRole() {
        if (this.getUsers() != null) {
            List<User> roleUsers = this.getUsers().stream().toList();
            roleUsers.forEach(this::removeUserFromRole);  ////this method will update the roles in each User.
        }
    }


    public String getName(){
        return name != null? name : "";
    }
}
