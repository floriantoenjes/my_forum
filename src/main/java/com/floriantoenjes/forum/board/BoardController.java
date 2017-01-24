package com.floriantoenjes.forum.board;

import com.floriantoenjes.forum.topic.TopicService;
import org.h2.mvstore.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("boards")
public class BoardController {
    @Autowired
    BoardService boardService;

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
            page = 1;
        }
        model.addAttribute("board", board);
        model.addAttribute("topics", topicService.findAll(new PageRequest(page, 5)));
        return "board";
    }

}
