package com.floriantoenjes.forum.user;

import com.floriantoenjes.forum.core.BaseEntity;
import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.topic.Topic;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User extends BaseEntity implements UserDetails {
    @Size(min = 3, max = 25)
    @Column(unique = true)
    private String username;

    @Size(min = 8, max = 25)
    private String password;

    @OneToMany
    private List<Topic> topics;

    @OneToMany
    private List<Post> posts;

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private Role role;

    public User() {
        this.topics = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public User(String username, String password, Role role) {
        this();
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(role.getName());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
