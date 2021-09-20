package ru.sunoplyaandesin.simplemessenger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {

    @GetMapping(value = {"/test"})
    public String index() {
        return "/test";
    }

    @GetMapping(value = {"/admin"})
    public String admin() {
        return "/admin";
    }

    @GetMapping(value = {"/login"})
    public String login() {
        return "/login";
    }
}
