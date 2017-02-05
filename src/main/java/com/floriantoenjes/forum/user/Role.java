package com.floriantoenjes.forum.user;

import com.floriantoenjes.forum.core.BaseEntity;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
public class Role extends BaseEntity{
    @Size(min = 5, max = 25)
    private String name;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
