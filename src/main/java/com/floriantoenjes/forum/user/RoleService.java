package com.floriantoenjes.forum.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public void save(Role role) {
        roleRepository.save(role);
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
