package org.springboot.springboot1.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl jr = new JdbcTokenRepositoryImpl();
        jr.setDataSource(dataSource);
        // 如果数据库尚未创建 persistent_logins 表，可设置为 true 让 JdbcTokenRepositoryImpl 尝试创建表。
        // init.sql 中已经包含建表语句，生产环境通常应保持 false。
        jr.setCreateTableOnStartup(false);
        return jr;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           PersistentTokenRepository persistentTokenRepository) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index", "/bookList", "/bookDetail/**",
                                 "/product/list", "/product/findByCategoryId/**", "/product/details/**",
                                 "/css/**", "/js/**", "/images/**", "/img/**",
                                 "/userLogin", "/login", "/error", "/register").permitAll()
                .requestMatchers("/addbook", "/editBook/**", "/deleteBook/**",
                                 "/product/add", "/product/edit/**", "/product/delete/**").hasRole("admin")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/userLogin")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .rememberMe(remember -> remember
                .rememberMeParameter("rememberme")
                // 将记住我有效期改为 7 天（按幻灯片 51-53 的常见示例）
                .tokenValiditySeconds(7 * 24 * 60 * 60)
                .tokenRepository(persistentTokenRepository)
            );
        // 自定义访问拒绝处理（403）
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        // 将被拒绝信息转发到 /403 并携带原始请求 URL 与异常信息以便页面展示
        return (request, response, accessDeniedException) -> {
            request.setAttribute("deniedUrl", request.getRequestURI());
            java.security.Principal principal = request.getUserPrincipal();
            request.setAttribute("deniedUser", principal != null ? principal.getName() : "匿名");
            request.setAttribute("deniedMessage", accessDeniedException != null ? accessDeniedException.getMessage() : "");
            // 转发到 403 视图，由 IndexController 的 /403 映射渲染
            request.getRequestDispatcher("/403").forward(request, response);
        };
    }
}
