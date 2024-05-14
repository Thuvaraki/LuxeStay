package com.project.backend.controller;

import com.project.backend.exception.RoleAlreadyExistException;
import com.project.backend.exception.UserAlreadyExistsException;
import com.project.backend.exception.UsernameNotFoundException;
import com.project.backend.model.Role;
import com.project.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin(origins = "http://localhost:3000")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/getRoles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> allRoles = roleService.getRoles();
        return ResponseEntity.ok(allRoles);
    }

    @PostMapping("/createNewRole")
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        try {
            roleService.createRole(role);
            return ResponseEntity.ok("New role created successfully!");
        } catch (RoleAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteRole/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId) {
        roleService.deleteRole(roleId);
    }

    @GetMapping("/getRoleByName/{name}")
    public ResponseEntity<Role> findByName(@PathVariable("name") String name) {
        Role theRole = roleService.findByName(name);
        return ResponseEntity.ok(theRole);
    }

    @DeleteMapping("/removeUserFromRole")
    public ResponseEntity<?> removeUserFromRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        try {
            return ResponseEntity.ok(roleService.removeUserFromRole(userId, roleId));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/removeAllUsersFromRole/{roleId}")
    public ResponseEntity<?> removeUserFromRole(@PathVariable Long roleId) {
        try {
            return ResponseEntity.ok(roleService.removeAllUsersFromRole(roleId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/assignUserToRole")
    public ResponseEntity<?> assignUserToRole(
            @RequestParam("userId") Long userId,
            @RequestParam("roleId") Long roleId){
        try{
           return ResponseEntity.ok(roleService.assignRoleToUser(userId, roleId));
        }
        catch (UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

