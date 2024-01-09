package ru.netology.Security_HW67_BasicAuth.controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.Security_HW67_BasicAuth.model.Post;
import ru.netology.Security_HW67_BasicAuth.service.PostService;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to our service! For more information you must log in";
    }

    @GetMapping("/all")
    public List<Post> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable long id) {
        return service.getById(id);
    }

    @GetMapping("/write")
    public String write () {
        return "authority write";
    }

    @PostMapping
    public Post save(@RequestBody Post post) {
        return service.save(post);
    }

    @GetMapping("/delete")
    public String delete() {
        return "authority delete";
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id) {
        service.removeById(id);
    }
}
