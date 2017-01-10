package com.floriantoenjes.forum.core;

import com.floriantoenjes.forum.board.Board;
import com.floriantoenjes.forum.board.BoardService;
import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.topic.Topic;
import com.floriantoenjes.forum.user.Role;
import com.floriantoenjes.forum.user.User;
import com.floriantoenjes.forum.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements ApplicationRunner {

    private BoardService boardService;

    private UserService userService;

    @Autowired
    public DatabaseLoader(BoardService boardService, UserService userService) {
        this.boardService = boardService;
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user1 = new User("user1", "password", new Role("ROLE_USER"));
        userService.save(user1);
        User user2 = new User("user2", "password", new Role("ROLE_USER"));
        userService.save(user2);


        Board board1 = new Board("First Board");
        Topic topic1 = new Topic("First Topic");
        Post post1 = new Post(user1,"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, " +
                "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, " +
                "sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet ");
        Post post2 = new Post(user2, "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et " +
                "accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est ");
        board1.addTopic(topic1);
        topic1.addPost(post1);
        topic1.addPost(post2);

        boardService.save(board1);
    }
}
