package com.floriantoenjes.forum.web;

import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public String searchResults(@RequestParam String query, @RequestParam(value = "page", required = false) Integer page,
                                Model model) {
        if (page == null) {
            page = 0;
        }
        Page<Post> resultPage = postService.findAll(new PageRequest(page, 10));
        List<Post> results = resultPage.getContent().stream().filter(post -> post.getText().toLowerCase()
                .contains(query.toLowerCase())).collect(Collectors.toList());

        ArrayList<Integer> pages = new ArrayList<>();
        IntStream.range(0, resultPage.getTotalPages()).forEach(pages::add);

        model.addAttribute("results", results);
        model.addAttribute("query", query);
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", page);
        return "search_results";
    }
}
