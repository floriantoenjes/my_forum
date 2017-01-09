package com.floriantoenjes.forum.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("topics")
public class TopicController {

    @Autowired
    TopicService topicService;

    @RequestMapping("/{id}")
    public String topic(@PathVariable Long id, Model model) {
        model.addAttribute("topic", topicService.findOne(id));
        return "topic";
    }

}
