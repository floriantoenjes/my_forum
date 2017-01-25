package com.floriantoenjes.forum.user;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public User findByUsername(String Username);

    public void save(User user);

    public List<User> findAll();
}
