package com.floriantoenjes.forum.web;

import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.post.PostService;
import com.floriantoenjes.forum.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("search")
public class SearchController {

    @Value("${com.floriantoenjes.forum.page-size}")
    private int PAGE_SIZE;

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


        Pagination<Post> pagination = new Pagination<>(posts, page, PAGE_SIZE);
        List<Integer> pages = pagination.getPages();
        List<Post> results = pagination.getElements();

        model.addAttribute("currentPage", page);
        model.addAttribute("pages", pages);
        model.addAttribute("query", query);
        model.addAttribute("results", results);

        return "search_results";
    }

}
