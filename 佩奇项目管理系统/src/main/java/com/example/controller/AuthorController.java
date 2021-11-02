package com.example.controller;

import com.example.common.Result;
import com.example.entity.Author;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    @Resource
    private AuthorService authorService;
    @Resource
    private HttpServletRequest request;

    public User getUser() {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("-1", "请登录");
        }
        return user;
    }

    @PostMapping
    public Result<?> save(@RequestBody Author author) {
        return Result.success(authorService.save(author));
    }

    @PutMapping
    public Result<?> update(@RequestBody Author author) {
        return Result.success(authorService.updateById(author));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(authorService.list());
    }

}
