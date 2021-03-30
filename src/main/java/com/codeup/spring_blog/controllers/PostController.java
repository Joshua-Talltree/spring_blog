package com.codeup.spring_blog.controllers;

import com.codeup.spring_blog.models.Post;
import com.codeup.spring_blog.models.User;
import com.codeup.spring_blog.repo.PostRepository;
import com.codeup.spring_blog.repo.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    private final PostRepository postDao;
    private final UserRepository userDao;


    public PostController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

//    List<Post> posts = new ArrayList<>();

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
    public String createPosts() {
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPostsHere(@RequestParam("post_title") String title, @RequestParam("post_body") String body) {
        Post postToSave = new Post(title, body);
        User user = userDao.getOne(1L);
        postDao.save(postToSave);
        postToSave.setOwner(user);
        return "redirect:/posts";
    }


    @GetMapping("/posts/{id}/update")
    public String updateAdForm(@PathVariable Long id, Model model){
        Post postsFromDB = postDao.getOne(id);
        model.addAttribute("oldPost", postsFromDB);
        return "posts/update";
    }

    @PostMapping("/post/{id}/update")
    @ResponseBody
    public String updatePost(@PathVariable Long id, @RequestParam("post_title") String title, @RequestParam("post_body") String body){
        Post postToSave = new Post(id, title, body);
        postDao.save(postToSave);
        return "redirect/posts";
    }

    @PostMapping("/posts/{id}/delete")
    @ResponseBody
    public String deletePost(@PathVariable Long id) {
        postDao.deleteById(id);
        return "post deleted";
    }
}