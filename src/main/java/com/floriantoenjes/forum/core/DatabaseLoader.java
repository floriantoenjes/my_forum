package com.floriantoenjes.forum.core;

import com.floriantoenjes.forum.board.Board;
import com.floriantoenjes.forum.board.BoardService;
import com.floriantoenjes.forum.topic.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements ApplicationRunner {

    private BoardService boardService;

    @Autowired
    public DatabaseLoader(BoardService boardService) {
        this.boardService = boardService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Board firstBoard = new Board("First Board");
        firstBoard.addTopic(new Topic("First Topic"));

        boardService.save(firstBoard);
    }
}
