package com.floriantoenjes.forum.board;

import com.floriantoenjes.forum.core.BaseEntity;
import com.floriantoenjes.forum.topic.Topic;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Board extends BaseEntity {
    private String name;

    @OneToMany
    private List<Topic> topics;
}
