package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    List<User> getuserlist(int id);
//分页
    List<User> getuserlistpage(String password);
}
