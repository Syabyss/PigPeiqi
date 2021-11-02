package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Author;
import com.example.mapper.AuthorMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthorService extends ServiceImpl<AuthorMapper, Author> {

    @Resource
    private AuthorMapper authorMapper;

}
