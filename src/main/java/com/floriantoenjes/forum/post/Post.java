package com.floriantoenjes.forum.post;

import com.floriantoenjes.forum.core.BaseEntity;
import com.floriantoenjes.forum.user.User;

import javax.persistence.ManyToOne;

public class Post extends BaseEntity {
    @ManyToOne
    private User author;

    private String text;
}
