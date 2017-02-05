package com.floriantoenjes.forum.topic;

import com.floriantoenjes.forum.board.Board;
import com.floriantoenjes.forum.board.BoardService;
import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.user.User;
import com.floriantoenjes.forum.user.UserService;
import com.floriantoenjes.forum.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("topics")
@Transactional
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private Validator validator;

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
    @Secured("ROLE_USER")
    public String addReply(@PathVariable Long id, Post reply, BindingResult result) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        reply.setAuthor(user);

        Topic topic = topicService.findOne(id);
        topic.addPost(reply);

        validator.validate(reply, result);
        topicService.save(topic);
        return "redirect:/topics/{id}";
    }

    @RequestMapping("/add")
    @Secured("ROLE_USER")
    public String newTopicForm(@RequestParam Long boardId, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        Board board = boardService.findOne(boardId);
        model.addAttribute("board", board);
        model.addAttribute("topic", new Topic());
        return "topic_form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Secured("ROLE_USER")
    public String addTopic(Topic topic, @RequestParam Long boardId, @RequestParam String firstPostText, BindingResult result) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        topic.setAuthor(user);

        boardService.findOne(boardId).addTopic(topic);
        validator.validate(topic, result);

        Post post = new Post();
        post.setAuthor(user);
        post.setText(firstPostText);
        topic.addPost(post);
        validator.validate(post, result);

        topicService.save(topic);
        return String.format("redirect:/topics/%s", topic.getId());
    }

}
