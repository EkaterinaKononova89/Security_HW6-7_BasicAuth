package ru.netology.Security_HW67_BasicAuth.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.netology.Security_HW67_BasicAuth.model.Post;
import ru.netology.Security_HW67_BasicAuth.service.PostService;

import javax.annotation.security.RolesAllowed;
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

    @PreAuthorize("#username == authentication.principal.username")
    @GetMapping("/greeting")
    public String greetingUser(@RequestParam String username) {
        return "Welcome to our service, " + username;
    }

    @Secured({"ROLE_READ"})
    @GetMapping("/all")
    public List<Post> all() {
        return service.all();
    }

    @Secured({"ROLE_READ"})
    @GetMapping("/{id}")
    public Post getById(@PathVariable long id) {
        return service.getById(id);
    }

    @RolesAllowed({"ROLE_WRITE"})
    @GetMapping("/write")
    public String write() {
        return "authority write";
    }

    @RolesAllowed({"ROLE_WRITE"})
    @PostMapping
    public Post save(@RequestBody Post post) {
        return service.save(post);
    }

    @PreAuthorize("hasAnyRole('WRITE', 'DELETE')")
    @GetMapping("/delete")
    public String delete() {
        return "authority delete";
    }

    @PreAuthorize("hasAnyRole('WRITE', 'DELETE')")
    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id) {
        service.removeById(id);
    }
}
