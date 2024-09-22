package org.javakov.gameskeystore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/{any}")
    public String redirect() {
        return "redirect:/";
    }
}
