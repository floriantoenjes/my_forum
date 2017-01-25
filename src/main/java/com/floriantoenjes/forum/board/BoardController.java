package com.floriantoenjes.forum.board;

import com.floriantoenjes.forum.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.stream.IntStream;

@Controller
@RequestMapping("boards")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private TopicService topicService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("boards", boardService.findAll());
        return "index";
    }

    @RequestMapping("/{id}")
    public String board(@PathVariable Long id, @RequestParam(value = "page", required = false) Integer page,  Model model) {
        Board board = boardService.findOne(id);
        if (page == null) {
            page = 0;
        }

        Page p = topicService.findByBoard(board, new PageRequest(page, 10));
        ArrayList<Integer> pages = new ArrayList<>();
        IntStream.range(0, p.getTotalPages()).forEach(pages::add);

        model.addAttribute("board", board);
        model.addAttribute("topics", p.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", page);
        return "board";
    }

}
