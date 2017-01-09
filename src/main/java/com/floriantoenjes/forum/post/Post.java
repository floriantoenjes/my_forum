package com.floriantoenjes.forum.post;

import com.floriantoenjes.forum.core.BaseEntity;
import com.floriantoenjes.forum.topic.Topic;
import com.floriantoenjes.forum.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Entity
public class Post extends BaseEntity {
    @ManyToOne
    private User author;

    @Size(max = 500)
    private String text;

    @ManyToOne
    Topic topic;

    public Post() {

    }

    public Post(User author, String text) {
        this.author = author;
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
