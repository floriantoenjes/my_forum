package com.floriantoenjes.forum.post;

import com.floriantoenjes.forum.core.BaseEntity;
import com.floriantoenjes.forum.topic.Topic;
import com.floriantoenjes.forum.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Post extends BaseEntity {
    @ManyToOne
    private User author;

    private String text;

    @ManyToOne
    Topic topic;

    public Post() {

    }

    public Post(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
