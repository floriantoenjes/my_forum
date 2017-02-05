package com.floriantoenjes.forum.topic;

import com.floriantoenjes.forum.board.Board;
import com.floriantoenjes.forum.core.BaseEntity;
import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Topic extends BaseEntity {
    @Size(min = 5, max = 50)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Post> posts;

    @ManyToOne
    @NotNull
    private User author;

    @ManyToOne
    @NotNull
    private Board board;

    public Topic() {
        this.posts = new ArrayList<>();
    }

    public Topic(User author, String name) {
        this();
        this.author = author;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean addPost(Post post) {
        post.setTopic(this);
        return posts.add(post);
    }
}
