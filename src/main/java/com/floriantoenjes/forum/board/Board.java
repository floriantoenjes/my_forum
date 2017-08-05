package com.floriantoenjes.forum.board;

import com.floriantoenjes.forum.core.BaseEntity;
import com.floriantoenjes.forum.topic.Topic;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Board extends BaseEntity {
    @Size(min = 3, max = 50)
    private String name;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Topic> topics;

    public Board() {
        this.topics = new ArrayList<>();
    }

    public Board(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public boolean addTopic(Topic topic) {
        topic.setBoard(this);
        return this.topics.add(topic);
    }

    public Topic getLastTopic() {
        if (topics.size() > 0) {
            return topics.get(topics.size() - 1);
        }
        return null;
    }
}
