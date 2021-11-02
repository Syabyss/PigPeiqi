package com.example.controller;

import com.example.common.Result;
import com.example.entity.Tag;
import com.example.service.TagService;
import com.example.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.example.exception.CustomException;
import cn.hutool.core.util.StrUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    @Resource
    private TagService tagService;
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
    public Result<?> save(@RequestBody Tag tag) {
        return Result.success(tagService.save(tag));
    }

    @PutMapping
    public Result<?> update(@RequestBody Tag tag) {
        return Result.success(tagService.updateById(tag));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        tagService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(tagService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(tagService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Tag> query = Wrappers.<Tag>lambdaQuery().orderByAsc(Tag::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Tag::getName, name);
        }
        return Result.success(tagService.page(new Page<>(pageNum, pageSize), query));
    }

}
