package com.example.controller;

import com.example.common.Result;
import com.example.entity.Link;
import com.example.service.LinkService;
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
@RequestMapping("/api/link")
public class LinkController {
    @Resource
    private LinkService linkService;
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
    public Result<?> save(@RequestBody Link link) {
        return Result.success(linkService.save(link));
    }

    @PutMapping
    public Result<?> update(@RequestBody Link link) {
        return Result.success(linkService.updateById(link));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        linkService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(linkService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(linkService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Link> query = Wrappers.<Link>lambdaQuery().orderByDesc(Link::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Link::getName, name);
        }
        return Result.success(linkService.page(new Page<>(pageNum, pageSize), query));
    }

}
