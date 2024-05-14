package com.project.backend.service;

import com.project.backend.model.Role;
import com.project.backend.model.User;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    Role createRole(Role theRole);

    void deleteRole(Long roleId);

    Role findByName(String name);

    User removeUserFromRole(Long userId,Long roleId);

    Role removeAllUsersFromRole(Long roleId);

    User assignRoleToUser(Long userId, Long roleId);
}
