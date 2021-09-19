package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Value("${path}")
    private String fileUpload;
    @Value("${path-audio}")
    private String fileAudio;
    @Autowired
    BlogService blogService;

    @ModelAttribute("newBlog")
    private Blog blog() {
        return new Blog();
    }

    @GetMapping("")
    public ModelAndView showAll() {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("blogs", blogService.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createBlogForm() {
        ModelAndView modelAndView = new ModelAndView("create");
        return modelAndView;
    }

    @PostMapping("/create")
    public String createBlog(@ModelAttribute("newBlog") Blog blog, MultipartFile image, MultipartFile audio) {
        String pathAudio = audio.getOriginalFilename();
        blog.setPathAudio(pathAudio);
        String pathImage = image.getOriginalFilename();
        blog.setPathImage(pathImage);
        try {
            FileCopyUtils.copy(audio.getBytes(), new File(fileAudio + pathAudio));
            FileCopyUtils.copy(image.getBytes(), new File(fileUpload + pathImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        blogService.save(blog);
        return "redirect:/blog";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        blogService.remove(id);
        return "redirect:/blog";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable Long id) {
        Blog blog = blogService.findById(id);
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("oldBlog", blog);
        return modelAndView;
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("odlBlog") Blog blog, MultipartFile image, MultipartFile audio) {
        Blog blog1 = blog;
        if (image.getSize()>0) {
            String pathImage = image.getOriginalFilename();
            blog.setPathImage(pathImage);
            try {
                FileCopyUtils.copy(image.getBytes(), new File(fileUpload + pathImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (image.getSize()>0) {
            String pathAudio = audio.getOriginalFilename();
            blog.setPathAudio(pathAudio);
            try {
                FileCopyUtils.copy(audio.getBytes(), new File(fileAudio + pathAudio));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        blogService.save(blog);
        return "redirect:/blog";
    }

}
