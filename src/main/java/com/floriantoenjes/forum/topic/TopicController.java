package com.floriantoenjes.forum.topic;

import com.floriantoenjes.forum.board.Board;
import com.floriantoenjes.forum.board.BoardService;
import com.floriantoenjes.forum.file.StorageService;
import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.post.PostService;
import com.floriantoenjes.forum.user.User;
import com.floriantoenjes.forum.user.UserService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private Validator validator;

    @Autowired
    private StorageService storageService;

    @Autowired
    private PostService postService;

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
        if (!model.containsAttribute("reply")) {
            model.addAttribute("reply", new Post());
        }
        return "topic";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @Secured("ROLE_USER")
    public String addQuickReply(@PathVariable Long id, Post reply, BindingResult result,
                           RedirectAttributes redirectAttributes) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        reply.setAuthor(user);

        Topic topic = topicService.findOne(id);
        topic.addPost(reply);

        validator.validate(reply, result);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reply", result);
            redirectAttributes.addFlashAttribute("reply", reply);
        } else {
            topicService.save(topic);
        }
        return "redirect:/topics/{id}";
    }

    @RequestMapping("/{id}/add")
    public String addReplyForm(@PathVariable Long id, Model model) {
        Topic topic = topicService.findOne(id);

        if (!model.containsAttribute("post")) {
            model.addAttribute("post", new Post());
        }
        model.addAttribute("action", String.format("/topics/%s/add", id));
        model.addAttribute("submit", "Add reply");

        return "post_form";
    }

    @RequestMapping(value = "/{topicId}/add", method = RequestMethod.POST)
    public String addReply(@PathVariable Long topicId, @RequestParam String text, @RequestParam MultipartFile file,
                           Post post, BindingResult result, RedirectAttributes redirectAttributes) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        post.setAuthor(userService.findByUsername(username));
        post.setText(text);

        Topic topic = topicService.findOne(topicId);
        topic.addPost(post);

        if (!file.isEmpty()) {
            try {
                if (ImageIO.read(file.getInputStream()) != null) {
                    post.addImage(
                            storageService.store(file)
                    );
                } else {
                    redirectAttributes.addFlashAttribute("imageUploadError", true);
                    redirectAttributes.addFlashAttribute("imageUploadErrorMessage",
                            "You can only upload images");
                    return "redirect:/topics/{id}/add";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        validator.validate(post, result);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.post", result);
            redirectAttributes.addFlashAttribute("post", post);
            return "redirect:/topics/{id}/add";
        }
        topicService.save(topic);

        return String.format("redirect:/topics/%s", post.getTopic().getId());
    }


    @RequestMapping("/add")
    @Secured("ROLE_USER")
    public String newTopicForm(@RequestParam Long boardId, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        Board board = boardService.findOne(boardId);
        model.addAttribute("board", board);
        if (!model.containsAttribute("topic")) {
            model.addAttribute("topic", new Topic());
        }
        return "topic_form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Secured("ROLE_USER")
    public String addTopic(Topic topic, @RequestParam Long boardId, @RequestParam String firstPostText, BindingResult result,
                           RedirectAttributes redirectAttributes) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        topic.setAuthor(user);

        boardService.findOne(boardId).addTopic(topic);
        validator.validate(topic, result);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.topic", result);
            redirectAttributes.addFlashAttribute("topic", topic);
            return String.format("redirect:/topics/add?boardId=%s", boardId);
        }

        Post post = new Post();
        post.setAuthor(user);
        post.setText(firstPostText);
        topic.addPost(post);
        validator.validate(post, result);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("firstPostTextError", true);
            redirectAttributes.addFlashAttribute("firstPostTextErrorMessage",
                    result.getFieldError("text").getDefaultMessage());
            redirectAttributes.addFlashAttribute("topic", topic);
            return String.format("redirect:/topics/add?boardId=%s", boardId);
        }

        topicService.save(topic);
        return String.format("redirect:/topics/%s", topic.getId());
    }

}
