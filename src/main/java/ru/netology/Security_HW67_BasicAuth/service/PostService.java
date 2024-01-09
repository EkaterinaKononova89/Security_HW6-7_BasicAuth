package ru.netology.Security_HW67_BasicAuth.service;

import org.springframework.stereotype.Service;
import ru.netology.Security_HW67_BasicAuth.exception.NotFoundException;
import ru.netology.Security_HW67_BasicAuth.model.Post;
import ru.netology.Security_HW67_BasicAuth.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public Post save(Post post) {
        Post update = repository.save(post);
        if (repository.save(post) == null) {
            throw new NotFoundException("Not found post with ID " + post.getId());
        }
        return update;
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}
