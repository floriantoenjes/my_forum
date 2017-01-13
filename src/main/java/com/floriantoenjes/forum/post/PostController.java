package com.floriantoenjes.forum.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("posts")
public class PostController {

    @Autowired
    PostService postService;

    @RequestMapping("/{id}")
    public String postForm(@PathVariable Long id, Model model) {
        Post post = postService.findOne(id);
        model.addAttribute("post", post);
        return "post";
    }

    @RequestMapping(value = "/{id}" ,method = RequestMethod.POST)
    public String updatePost(@PathVariable Long id, @RequestParam String text) {
        Post post = postService.findOne(id);
        post.setText(text);
        postService.save(post);

        return String.format("redirect:/topics/%s", post.getTopic().getId());
    }

}
