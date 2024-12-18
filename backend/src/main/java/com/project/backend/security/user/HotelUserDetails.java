package com.project.backend.security.user;

import com.project.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


//This class represents user details needed for security purposes within a Java application
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelUserDetails implements UserDetails { //implements the UserDetails interface
    private Long userId;
    private String email;
    private String password;

    private Collection<GrantedAuthority> authorities;

    public static HotelUserDetails buildUserDetails(User user){ //static method
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new HotelUserDetails(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    //Overrides of UserDetails Methods; These methods are used by Spring Security to determine user status
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
