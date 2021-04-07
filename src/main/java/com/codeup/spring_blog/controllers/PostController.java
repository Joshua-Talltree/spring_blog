package com.codeup.spring_blog.controllers;

import com.codeup.spring_blog.models.Post;
import com.codeup.spring_blog.models.User;
import com.codeup.spring_blog.repo.PostRepository;
import com.codeup.spring_blog.repo.UserRepository;
import com.codeup.spring_blog.services.EmailServices;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    private final PostRepository postDao;
    private final EmailServices emailService;
    private final UserRepository userDao;


    public PostController(PostRepository postDao, UserRepository userDao, EmailServices emailService) {
        this.postDao = postDao;
        this.emailService = emailService;
        this.userDao = userDao;
    }

    @GetMapping("/posts")
    public String allPosts(Model viewModel) {
        List<Post> postsFromDB = postDao.findAll();
        viewModel.addAttribute("posts", postsFromDB);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String individualPosts(@PathVariable Long id, Model vModel) {
        vModel.addAttribute("post", postDao.getOne(id));
        return "posts/show";
    }


    @GetMapping("/posts/create")
    public String createPosts(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPostsHere(@ModelAttribute Post postToCreate) {

        User userToAdd = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // save the post
        postDao.save(postToCreate);
        // set the user
        postToCreate.setOwner(userToAdd);
        Post savedPost = postDao.save(postToCreate);
        emailService.prepareAndSend(savedPost,"Here is the title", "Here is the body");
        return "redirect:/posts";
    }


    @GetMapping("/posts/{id}/update")
    public String updatePostForm(Model model, @PathVariable Long id){
        model.addAttribute("post", postDao.getOne(id));
        return "posts/create";
    }

    @PostMapping("/posts/{id}/update")
    public String updatePost(@ModelAttribute Post postToUpdate, @PathVariable String id){

        User userToAdd = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println(id);
        System.out.println(userToAdd.getUsername());
        System.out.println(postToUpdate.getTitle());
        postToUpdate.setId(Long.parseLong(id));

        // set the user
        postToUpdate.setOwner(userToAdd);

        // save the post
        postDao.save(postToUpdate);

        return "redirect:/posts";
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postDao.deleteById(id);
        return "redirect:/posts";
    }

    @GetMapping("/posts/search")
    public String searchByKeyword(Model model, @RequestParam(name = "search") String term){
        model.addAttribute("posts",postDao.searchByBodyLike(term))  ;
        model.addAttribute("posts", postDao.findAll());
        return "posts/index";
    }
}