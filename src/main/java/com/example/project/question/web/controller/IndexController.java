package com.example.project.question.web.controller;

import com.example.project.direction.entity.UserEntity;
import com.example.project.question.service.PostsService;
import com.example.project.question.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());

        UserEntity user = (UserEntity) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("username", user.getUserName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}