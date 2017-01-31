package com.floriantoenjes.forum.web;

import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("search")
public class SearchController {
    @Autowired
    PostService postService;

    @RequestMapping("/form")
    public String searchForm() {
        return "search_form";
    }

    @RequestMapping("/results")
    public String searchResults(@RequestParam String query, Model model) {
        List<Post> results = postService.findAll().stream().filter(post -> post.getText().toLowerCase()
                .contains(query.toLowerCase())).collect(Collectors.toList());
        model.addAttribute("results", results);
        return "search_results";
    }
}
