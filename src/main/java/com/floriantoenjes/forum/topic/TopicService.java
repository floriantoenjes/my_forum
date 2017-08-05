package com.floriantoenjes.forum.topic;

import com.floriantoenjes.forum.board.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> findAll() {
        return (List<Topic>) topicRepository.findAll();
    }

    public Page findAll(PageRequest pageRequest) {
        return topicRepository.findAll(pageRequest);
    }

    public Topic findOne(Long id) {
        return topicRepository.findOne(id);
    }

    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    public Page<Topic> findByBoard(Board board, Pageable pageable) {
        return topicRepository.findByBoard(board, pageable);
    }

    public Page<Topic> findByBoardOrderByLastPostDate(Board board, Pageable pageable) {
        return topicRepository.findByBoardOrderByLastPostDateDesc(board, pageable);
    }
}
