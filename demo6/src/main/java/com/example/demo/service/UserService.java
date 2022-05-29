package com.example.demo.service;

import com.example.demo.pojo.User;
import com.example.demo.pojo.query.UserQuery;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface UserService {
    PageInfo<User> getAllUser(UserQuery userQuery);
    long addUser(User user);
    void dwonload(String data,String FilePathAndName) throws IOException;
}
