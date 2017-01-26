package com.floriantoenjes.forum.topic;

import com.floriantoenjes.forum.board.Board;
import com.floriantoenjes.forum.board.BoardService;
import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.user.User;
import com.floriantoenjes.forum.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("topics")
public class TopicController {

    @Autowired
    TopicService topicService;

    @Autowired
    UserService userService;

    @Autowired
    BoardService boardService;

    @RequestMapping("/{id}")
    public String topic(@PathVariable Long id, Model model) {
        Topic topic = topicService.findOne(id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);

        Map<Post, Boolean> postMap = new LinkedHashMap<>();
        topic.getPosts().forEach(post -> {
            if (user == post.getAuthor()) {
                postMap.put(post, true);
            } else {
                postMap.put(post, false);
            }
        });

        model.addAttribute("topic", topic);
        model.addAttribute("postMap", postMap);
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
        return "redirect:/topics/{id}";
    }

    @RequestMapping("/add")
    public String newTopicForm(@RequestParam Long boardId, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        if (user==null) {
            return String.format("redirect:/boards/%s", boardId);
        }

        Board board = boardService.findOne(boardId);
        model.addAttribute("board", board);
        model.addAttribute("topic", new Topic());
        return "topic_form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addTopic(Topic topic, @RequestParam Long boardId, @RequestParam String firstPostText) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        topic.setAuthor(user);
        if (user==null) {
            return String.format("redirect:/boards/%s", boardId);
        }

        boardService.findOne(boardId).addTopic(topic);
        Post post = new Post();
        post.setAuthor(user);
        post.setText(firstPostText);
        topic.addPost(post);
        topic.setAuthor(user);
        topicService.save(topic);
        return String.format("redirect:/topics/%s", topic.getId());
    }

}
