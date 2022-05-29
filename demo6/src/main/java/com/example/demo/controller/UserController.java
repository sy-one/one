package com.example.demo.controller;
import com.example.demo.pojo.User;
import com.example.demo.pojo.query.UserQuery;
import com.example.demo.pojo.query.parameterOne;
import com.example.demo.pojo.query.parameterTwo;
import com.example.demo.service.impl.UserServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.*;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @GetMapping("/all")
    public String GetAllUser(Model model, parameterOne one, parameterTwo two, UserQuery userQuery){
        PageInfo<User> userPageInfo = userService.getAllUser(userQuery);
        model.addAttribute("page",userPageInfo);
        model.addAttribute("one",one);
        model.addAttribute("two",two);
        return "index";
    }
    @PostMapping("/add")
    public String ok(User user,RedirectAttributes attributes){
        userService.addUser(user);
        return "redirect:/all";
    }
    @PostMapping("/download")
    public String download(String name) throws IOException {
        userService.dwonload(name,"D:/test.xls");
        return "redirect:/all";
    }
    @PostMapping("/upload")
    public String hello(MultipartFile fileUpload) throws IOException {
        fileUpload.transferTo(new File("D:\\upload\\"+fileUpload.getOriginalFilename()));
        return "redirect:/all";

    }
}
