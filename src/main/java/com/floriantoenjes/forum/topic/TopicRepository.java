package com.floriantoenjes.forum.topic;

import com.floriantoenjes.forum.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TopicRepository extends PagingAndSortingRepository<Topic, Long> {
    public Page<Topic> findByBoard(Board board, Pageable pageable);

    public Page<Topic> findByBoardOrderByDateDesc(Board board, Pageable pageable);
}
