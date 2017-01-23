package com.floriantoenjes.forum.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    public List<Board> findAll() {
        return (List<Board>) boardRepository.findAll();
    }

    public List<Board> findAll(PageRequest pageRequest) {
        return (List<Board>) boardRepository.findAll(pageRequest);
    }

    public Board findOne(Long id) {
        return boardRepository.findOne(id);
    }

    public void save(Board board) {
        boardRepository.save(board);
    }
}
