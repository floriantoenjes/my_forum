package com.floriantoenjes.forum.topic;

import com.floriantoenjes.forum.board.Board;
import com.floriantoenjes.forum.core.BaseEntity;
import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Topic extends BaseEntity {
    @Size(min = 5, max = 50)
    private String name;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<Post> posts;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @NotNull
    private User author;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @NotNull
    private Board board;

    @NotNull
    private Date date = new Date();

    @OneToOne
    private Post lastPost;

    private Date lastPostDate = new Date();

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
        lastPost = post;
        lastPostDate = post.getDate();
        board.setLastTopic(this);
        return posts.add(post);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Post getLastPost() {
        return lastPost;
    }

    public void setLastPost(Post lastPost) {
        this.lastPost = lastPost;
    }

    public Integer getLastPage() {
        int PAGE_SIZE = 10;
        return (int) Math.ceil( (double) (posts.size()) / PAGE_SIZE);
    }

    public Date getLastPostDate() {
        return lastPostDate;
    }

    public void setLastPostDate(Date lastPostDate) {
        this.lastPostDate = lastPostDate;
    }
}
