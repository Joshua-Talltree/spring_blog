package com.codeup.spring_blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {

    @GetMapping("/posts")
    @ResponseBody
    public String allPosts(){
        return "All Post Here!";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String individualPosts(@PathVariable int id){
        return "Here is post from the id of: " + id + ".";
    }


    @GetMapping("/posts/create")
    @ResponseBody
    public String createPosts(){
        return "Here is where you create posts";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createPostsHere(){
        return "You will submit a post here";
    }
}
