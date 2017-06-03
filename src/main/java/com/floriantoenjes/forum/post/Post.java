package com.floriantoenjes.forum.post;

import com.floriantoenjes.forum.core.BaseEntity;
import com.floriantoenjes.forum.topic.Topic;
import com.floriantoenjes.forum.user.User;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Post extends BaseEntity {
    @ManyToOne
    @NotNull
    private User author;

    @Size(min = 3, max = 500)
    private String text;

    @ManyToOne
    @NotNull
    private Topic topic;

    @NotNull
    private Date date = new Date();

    @ElementCollection(targetClass = String.class)
    private List<String> images = new ArrayList<>();

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void addImage(String image) {
        images.add(image);
    }
}
