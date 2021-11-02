package com.example.service;

import com.example.entity.Link;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.LinkMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LinkService extends ServiceImpl<LinkMapper, Link> {

    @Resource
    private LinkMapper linkMapper;

}
