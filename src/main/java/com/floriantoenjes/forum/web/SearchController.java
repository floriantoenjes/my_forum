package com.floriantoenjes.forum.web;

import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("search")
public class SearchController {
    @Autowired
    private PostService postService;

    @RequestMapping("/form")
    public String searchForm() {
        return "search_form";
    }

    @RequestMapping("/results")
    @SuppressWarnings("unchecked")
    public String searchResults(@RequestParam String query,
                                @RequestParam(value = "page", required = false, defaultValue = "0")
                                        Integer page, Model model) {

        final int PAGE_SIZE = 10;
        final int TEXT_LENGTH = 50 / 2;
        final String ELLIPSIS = "...";

        List<Post> posts = new ArrayList<>();
        for (Post post : postService.findAll()) {

            if (post.getText().toLowerCase().contains(query.toLowerCase())) {
                Pattern pattern = Pattern.compile(String.format("(.{0,%d}%s.{0,%d})",
                        TEXT_LENGTH, query, TEXT_LENGTH));

                Matcher matcher = pattern.matcher(post.getText());

                if (matcher.find()) {
                    if (matcher.group().length() != post.getText().length()) {
                        if (matcher.start() == 0) {
                            post.setText(matcher.group() + ELLIPSIS);
                        } else if (matcher.hitEnd()) {
                            post.setText(ELLIPSIS + matcher.group());
                        } else {
                            post.setText(ELLIPSIS + matcher.group() + ELLIPSIS);
                        }
                    }

                }
                posts.add(post);
            }
        }

        /* Pagination */
        int startIndex = page * PAGE_SIZE;
        int endIndex;

        // More or same than fits on to the page? Make it page sized
        if (posts.size() >= (page + 1) * PAGE_SIZE) {
            endIndex = (page + 1) * PAGE_SIZE;

        // Less than fits on to the page? Take the remainder
        } else {
            endIndex = startIndex + (posts.size() % PAGE_SIZE);
        }
        List<Post> results = posts.subList(startIndex, endIndex);

        List<Integer> pages = new ArrayList<>();
        IntStream.range(0, (int) Math.ceil(posts.size() / (double) PAGE_SIZE)).forEach(pages::add);

        model.addAttribute("results", results);
        model.addAttribute("query", query);
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", page);

        return "search_results";
    }
}
