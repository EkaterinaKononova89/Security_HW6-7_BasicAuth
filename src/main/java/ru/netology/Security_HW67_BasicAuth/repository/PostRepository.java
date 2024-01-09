package ru.netology.Security_HW67_BasicAuth.repository;

import org.springframework.stereotype.Repository;
import ru.netology.Security_HW67_BasicAuth.exception.NotFoundException;
import ru.netology.Security_HW67_BasicAuth.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private static final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private static final AtomicLong cnt = new AtomicLong(0);

    public List<Post> all() {
        if (posts.isEmpty()) {
            return List.of(new Post(0, "List is empty"));
        }
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        if (posts.containsKey(id)) {
            return Optional.of(posts.get(id));
        } else {
            return Optional.empty();
        }
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(cnt.incrementAndGet());
            posts.put(post.getId(), post);
        } else if (post.getId() != 0) {
            if (posts.containsKey(post.getId())) {
                posts.replace(post.getId(), post);
            } else {
                return null;
            }
        }
        return post;
    }

    public void removeById(long id) throws NotFoundException {
        if (posts.containsKey(id)) {
            posts.remove(id);
            return;
        }
        throw new NotFoundException("Delete exception. Post with ID " + id + " not found");
    }
}
