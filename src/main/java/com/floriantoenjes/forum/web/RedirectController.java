package com.floriantoenjes.forum.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {

    @RequestMapping("/")
    public String redirectToBoards() {
        return "redirect:/boards/";
    }
}

