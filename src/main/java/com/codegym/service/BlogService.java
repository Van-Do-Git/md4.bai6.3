package com.codegym.service;

import com.codegym.model.Blog;
import com.codegym.repository.IBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BlogService {
    @Autowired
    IBlogRepository blogRepository;

    public List<Blog> findAll() {
        return blogRepository.findAll();
    }


    public Blog findById(Long id) {
        return blogRepository.findById(id);
    }


    public void save(Blog e) {
        blogRepository.save(e);
    }

    public void remove(Long id) {
        blogRepository.remove(id);
    }

    ;
}
