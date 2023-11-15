package com.sparta.project_todo.controller;

import com.sparta.project_todo.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class ToDoHomeController {
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username", userDetails.getUsername());

        return "index";
    }
}
