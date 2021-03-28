package com.codeup.spring_blog.controllers;

import com.codeup.spring_blog.models.Post;
import com.codeup.spring_blog.repo.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private final PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }

//    List<Post> posts = new ArrayList<>();

    @GetMapping("/posts")
    public String allPosts(Model viewModel) {
        List<Post> postsFromDB = postDao.findAll();
        viewModel.addAttribute("posts", postsFromDB);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String individualPosts(@PathVariable int id, Model vModel) {
        vModel.addAttribute("post", postDao);
        return "posts/show";
    }


    @GetMapping("/posts/create")
    public String createPosts() {
        return "Here is where you create posts";
    }

    @PostMapping("/posts/create")
    public String createPostsHere(Model vModel) {
        vModel.addAttribute("post", postDao);
        return "posts";
    }

    @PostMapping("/posts/delete")
    public String deletePostsById(@RequestParam long id, Model model) {
        postDao.deleteById(id);
        model.addAttribute("posts", postDao.findAll());
        return "/posts/index";
    }

    @PostMapping("/post/edit/{id}")
    public String editPostById(@ModelAttribute Post post) {
        postDao.save(post);
        return "redirect:/posts";
    }
}