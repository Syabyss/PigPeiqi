package com.example.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.Department;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;
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
    public Result<?> save(@RequestBody Department department) {
        return Result.success(departmentService.save(department));
    }

    @PutMapping
    public Result<?> update(@RequestBody Department department) {
        return Result.success(departmentService.updateById(department));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        departmentService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(departmentService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(departmentService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Department> query = Wrappers.<Department>lambdaQuery().orderByAsc(Department::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Department::getName, name);
        }
        return Result.success(departmentService.page(new Page<>(pageNum, pageSize), query));
    }
}
