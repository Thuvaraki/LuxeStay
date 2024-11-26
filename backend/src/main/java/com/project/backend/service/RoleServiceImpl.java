package com.project.backend.service;

import com.project.backend.exception.RoleAlreadyExistException;
import com.project.backend.exception.UserAlreadyExistsException;
import com.project.backend.exception.UsernameNotFoundException;
import com.project.backend.model.Role;
import com.project.backend.model.User;
import com.project.backend.repository.RoleRepository;
import com.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        String roleName = "ROLE_"+theRole.getName().toUpperCase();
        //Optional class is a container that may or may not contain a non-null value
//         part of the java.util package , used to  avoiding NullPointerException.
        Optional<Role> existingRole = roleRepository.findByName(roleName);
        if (existingRole.isPresent()) {  //if the Optional is empty, trying to retrieve value will throw a NoSuchElementException. so that  we  use isPresent() to check whether values exist
            throw new RoleAlreadyExistException(theRole.getName()+" role already exists");
        }

        Role role = new Role(roleName);
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.removeAllUsersFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

//    In Java's Optional class, the get() method is used to retrieve the value stored within the Optional if it is present.
//    However, if the Optional is empty, calling get() will throw a NoSuchElementException.
//    so that before using get(), we can use isPresent() to check whether values exist
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).get();
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if(role.isPresent() && role.get().getUsers().contains(user.get())){
            role.get().removeUserFromRole(user.get()); //invoking the removeUserFromRole method in Role object
            roleRepository.save(role.get());
            userRepository.save(user.get());
            return user.get();
        }
        else{
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent()) {
            role.get().removeAllUsersFromRole(); //invoking the removeAllUsersFromRole method in Role object
            return roleRepository.save(role.get());
        } else {
            throw new RuntimeException("Role not found with id: " + roleId);
        }
    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);

        if (user.isEmpty() || role.isEmpty()) {
            throw new RuntimeException("User or Role not found");
        }

        if(user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new UserAlreadyExistsException(
                    user.get().getFirstName()+ " is already assigned to the" + role.get().getName()+ " role");
        }

        if (role.isPresent()){
            role.get().assignRoleToUser(user.get()); //invoking the removeAllUsersFromRole method in Role object
            roleRepository.save(role.get());
            userRepository.save(user.get());

        }
        return user.get();
    }
}
