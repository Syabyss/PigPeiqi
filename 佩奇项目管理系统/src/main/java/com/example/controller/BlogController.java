package com.example.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.Blog;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.service.BlogService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogController {
    @Resource
    private BlogService blogService;
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
    public Result<?> save(@RequestBody Blog blog) {
        User user = getUser();
        blog.setAuthor(user.getUsername());
        blog.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return Result.success(blogService.save(blog));
    }

    @PutMapping
    public Result<?> update(@RequestBody Blog blog) {
        blog.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return Result.success(blogService.updateById(blog));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        blogService.removeById(id);
        return Result.success();
    }


    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        Blog blog = blogService.getById(id);
        blog.setView(blog.getView() + 1);
        blogService.updateById(blog);
        return Result.success(blog);
    }

    @GetMapping("/carousel")
    public Result<?> findCarousel() {
        LambdaQueryWrapper<Blog> query = Wrappers.<Blog>lambdaQuery()
                .like(Blog::getCarousel, "是")
                .orderByDesc(Blog::getId);
        return Result.success(blogService.page(new Page<>(1, 5), query));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(blogService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false, defaultValue = "") String tag,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Blog> query = Wrappers.<Blog>lambdaQuery().orderByDesc(Blog::getId);
        if (!StringUtils.isEmpty(name)) {
            query.like(Blog::getTitle, name);
        }
        if (!StringUtils.isEmpty(tag)) {
            query.eq(Blog::getTag, tag);
        }
        return Result.success(blogService.page(new Page<>(pageNum, pageSize), query));
    }

}
