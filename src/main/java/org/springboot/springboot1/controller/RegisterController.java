package org.springboot.springboot1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springboot.springboot1.entity.User;
import org.springboot.springboot1.service.IUserService;

@Controller
public class RegisterController {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String toRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam String username, @RequestParam String password, Model model) {
        // 简单校验
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            model.addAttribute("error", "用户名或密码不能为空");
            return "register";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        // 默认分配 ROLE_common（init.sql 中 role_id=2）
        try {
            userService.createUser(user, 2);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        } catch (Exception ex) {
            model.addAttribute("error", "注册失败，请稍后重试");
            return "register";
        }
        model.addAttribute("msg", "注册成功，请登录");
        return "login";
    }
}

