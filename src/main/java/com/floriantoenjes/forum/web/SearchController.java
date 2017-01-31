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
import java.util.stream.LongStream;

@Controller
@RequestMapping("search")
public class SearchController {
    private final int PAGE_SIZE = 10;

    @Autowired
    private PostService postService;

    @RequestMapping("/form")
    public String searchForm() {
        return "search_form";
    }

    @RequestMapping("/results")
    @SuppressWarnings("unchecked")
    public String searchResults(@RequestParam String query,
                                @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                Model model) {
        List<Post> posts = postService.findAll().stream().filter(post -> post.getText().toLowerCase()
                .contains(query.toLowerCase())).collect(Collectors.toList());

        int startIndex = page * 10;
        int endIndex;
        if (posts.size() > (page + 1) * 10){
            endIndex = (page + 1) * 10;
        } else {
            endIndex = startIndex + (posts.size() % 10);
        }
        List<Post> results = posts.subList(startIndex, endIndex);

        ArrayList<Integer> pages = new ArrayList<>();
        IntStream.range(0, (int) Math.ceil(posts.size() / 10.0)).forEach(pages::add);

        model.addAttribute("results", results);
        model.addAttribute("query", query);
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", page);
        return "search_results";
    }
}
