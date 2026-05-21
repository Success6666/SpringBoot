package org.springboot.springboot1.controller;

import java.security.Principal;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String toIndex(Model model, Principal principal) {
        addAuthInfo(model, principal);
        return "index";
    }

    @GetMapping("/userLogin")
    public String toLogin() {
        return "login";
    }

    @GetMapping("/index")
    public String toIndex2(Model model, Principal principal) {
        addAuthInfo(model, principal);
        return "index";
    }

    @GetMapping("/403")
    public String accessDenied(Model model, Principal principal) {
        addAuthInfo(model, principal);
        // 如果 AccessDeniedHandler 已经在 request attribute 中放了信息，优先显示
        // 这些属性在 SecurityConfig.accessDeniedHandler 中设置
        // 注意：在直接访问 /403 时这些属性 可能为 null
        return "403";
    }

    @ResponseBody
    @GetMapping("/invalid")
    public String invalid(HttpServletRequest req) {
        req.getSession().invalidate();
        return "session 失效";
    }

    private void addAuthInfo(Model model, Principal principal) {
        model.addAttribute("isAuthenticated", principal != null);
        if (principal instanceof Authentication auth) {
            model.addAttribute("username", auth.getName());
            model.addAttribute("isAdmin", auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_admin")));
            model.addAttribute("roles", auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
        }
    }
}
