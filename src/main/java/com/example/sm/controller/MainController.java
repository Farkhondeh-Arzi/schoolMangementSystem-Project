package com.example.sm.controller;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MainController {

    @GetMapping("/signup")
    public Link signUp(HttpServletRequest request, HttpServletResponse response) {

        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");

        response.setHeader("X-CSRF-TOKEN", token.getToken());

        return WebMvcLinkBuilder.linkTo(CollegeController.class).withSelfRel();
    }
}
