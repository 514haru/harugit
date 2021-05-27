package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.util.PageResult;
import com.example.demo.util.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> getuserlist(int id)
    {
        return userMapper.getuserlist(id);
    }

//    public PageResult findPage(PageRequest pageRequest) {
//        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
//    }

    public PageResult getuserlistpage(int pageNum, int pageSize, String password) {
//        int pageNum = pageRequest.getPageNum();
//        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.getuserlistpage(password);
        return PageUtils.getPageResult(new PageInfo<User>(users));

    }
}
