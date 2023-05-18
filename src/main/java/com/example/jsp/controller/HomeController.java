package com.example.jsp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.jsp.service.HomeService;

@Controller
public class HomeController {
    @Autowired
    private HomeService homeService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name", "myName");
        model.addAttribute("time", homeService.getTime());
        return "index";
    }

    @GetMapping("list")
    public void getList(Model model) {
        model.addAttribute("list", homeService.getList());
    }
}
