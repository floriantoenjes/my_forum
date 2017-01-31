package com.floriantoenjes.forum.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public List<Post> findAll() {
        return (List<Post>) postRepository.findAll();
    }

    public Page findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post findOne(Long id) {
        return postRepository.findOne(id);
    }

    public void save(Post post) {
        postRepository.save(post);
    }
}
