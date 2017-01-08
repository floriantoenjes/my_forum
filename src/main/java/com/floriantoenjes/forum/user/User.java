package com.floriantoenjes.forum.user;

import com.floriantoenjes.forum.core.BaseEntity;
import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.topic.Topic;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class User extends BaseEntity {
    private String username;

    private String password;

    @OneToMany
    List<Topic> topics;

    @OneToMany
    List<Post> posts;
}
