package com.floriantoenjes.forum.topic;

import com.floriantoenjes.forum.core.BaseEntity;
import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Topic extends BaseEntity {
    private String title;

    @OneToMany
    private List<Post> posts;

    @ManyToOne
    private User author;
}
