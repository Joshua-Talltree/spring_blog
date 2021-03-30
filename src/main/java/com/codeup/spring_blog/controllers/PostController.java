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
    public String createPosts(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPostsHere(@ModelAttribute Post post) {
        User userToAdd = userDao.getOne(2L);

        post.setOwner(userToAdd);

        postDao.save(post);
        return "redirect:/posts";
    }


    @GetMapping("/posts/{id}/update")
    public String updateAdForm(Model model, @PathVariable Long id){
        model.addAttribute("oldPost", postDao.getOne(id));
        return "posts/update";
    }

    @PostMapping("/post/{id}/update")
    @ResponseBody
    public String updatePost(@ModelAttribute Post post){
        postDao.save(post);
        return "redirect:/posts";
    }

    @PostMapping("/posts/{id}/delete")
    @ResponseBody
    public String deletePost(@PathVariable Long id) {
        postDao.deleteById(id);
        return "post deleted";
    }

    @GetMapping("/posts/search")
    public String searchByKeyword(Model model, @RequestParam(name = "search") String term){
        List<Post> posts = postDao.searchByBodyLike(term);
        model.addAttribute("posts", posts);

        return "posts/index";
    }
}