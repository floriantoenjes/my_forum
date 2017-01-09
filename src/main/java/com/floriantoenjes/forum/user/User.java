package com.floriantoenjes.forum.user;

import com.floriantoenjes.forum.core.BaseEntity;
import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.topic.Topic;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends BaseEntity {
    private String username;

    private String password;

    @OneToMany
    List<Topic> topics;

    @OneToMany
    List<Post> posts;

    public User() {
        this.topics = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public User(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

}
