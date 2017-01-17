package com.floriantoenjes.forum.board;

import com.floriantoenjes.forum.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String board(@PathVariable Long id, Model model) {
        Board board = boardService.findOne(id);

        model.addAttribute("board", board);
        return "board";
    }

}
