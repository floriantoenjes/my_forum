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

import java.util.stream.IntStream;

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
        final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et " +
                "accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est ";

        User user1 = new User("user1", "password", new Role("ROLE_USER"));
        userService.save(user1);
        User user2 = new User("user2", "password", new Role("ROLE_USER"));
        userService.save(user2);


        Board board1 = new Board("First Board");
        Board board2 = new Board("Second Board");

        Topic topic1 = new Topic(user1,"First Topic");

        Post post1 = new Post(user1, "First Post");

        Post post2 = new Post(user2, "Second Post");

        board1.addTopic(topic1);
        topic1.addPost(post1);
        topic1.addPost(post2);


        String[] templates = {
                "What is %s?",
                "Having trouble running %s",
                "%s resources",
                "%s is awesome"
        };

        String[] buzzwords = {
                "Java",
                "Scala",
                "Linux"
        };

        IntStream.range(0, 20)
                .forEach(i -> {
                    String template = templates[i % templates.length];
                    String buzzword = buzzwords[i % buzzwords.length];
                    Topic t = new Topic(user1, String.format(template, buzzword));
                    t.addPost(new Post(user1, LOREM_IPSUM));
                    board1.addTopic(t);
                });


        boardService.save(board1);
        boardService.save(board2);
    }
}
