package com.floriantoenjes.forum.topic;

import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.user.Role;
import com.floriantoenjes.forum.user.User;
import com.floriantoenjes.forum.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("topics")
public class TopicController {

    @Autowired
    TopicService topicService;

    @Autowired
    UserService userService;

    @RequestMapping("/{id}")
    public String topic(@PathVariable Long id, Model model) {
        model.addAttribute("topic", topicService.findOne(id));
        // ToDo: Add a map which states if user is author of post
        model.addAttribute("reply", new Post());
        return "topic";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String addReply(@PathVariable Long id, Post reply) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            // ToDo: Add Flashmessage
        } else {
            reply.setAuthor(user);
            Topic topic = topicService.findOne(id);
            topic.addPost(reply);
            topicService.save(topic);
        }
        return String.format("redirect:/topics/%s", id);
    }

}