package com.project.backend.security.user;

import com.project.backend.model.User;
import com.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//This class is responsible for loading user-specific data during the authentication process.
@Service
public class HotelUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return HotelUserDetails.buildUserDetails(user);
    }
}
//loadUserByUsername(String email): This method is called by Spring Security to load user details based on the provided email address during login/authentication.
// It retrieves the user from the database using the UserRepository and then converts it into a HotelUserDetails object using the buildUserDetails method.