package com.floriantoenjes.forum.post;

import com.floriantoenjes.forum.user.User;
import com.floriantoenjes.forum.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @RequestMapping("/{id}")
    public String postForm(@PathVariable Long id, Model model) {
        Post post = postService.findOne(id);
        model.addAttribute("post", post);
        return "post_form";
    }

    @RequestMapping(value = "/{id}" ,method = RequestMethod.POST)
    @Secured("ROLE_USER")
    public String updatePost(@PathVariable Long id, @RequestParam String text) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        Post post = postService.findOne(id);

        // ToDo: Add post validation here
        if (user == post.getAuthor()) {
            post.setText(text);
            postService.save(post);
        }

        return String.format("redirect:/topics/%s", post.getTopic().getId());
    }

}
