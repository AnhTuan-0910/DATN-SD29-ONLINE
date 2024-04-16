package com.springboot.bootstrap.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
        httpSecurity.authorizeHttpRequests((authorize)->{
            authorize.requestMatchers(mvc.pattern("/registration**")).permitAll();
            authorize.requestMatchers(mvc.pattern("/js/**")).permitAll();
            authorize.requestMatchers(mvc.pattern("/fe/**")).permitAll();
            authorize.requestMatchers(mvc.pattern("/css/**")).permitAll();
            authorize.requestMatchers(mvc.pattern("/img/**")).permitAll();
            authorize.requestMatchers(mvc.pattern("/gio-hang/**")).hasRole("USER");
            authorize.requestMatchers(mvc.pattern("/shop/**")).permitAll();
            authorize.requestMatchers(mvc.pattern("/shop/user/**")).hasRole("USER");
            authorize.requestMatchers(mvc.pattern("/thanh-toan/**")).hasRole("USER");
            authorize.requestMatchers(mvc.pattern("/spOnl/**")).permitAll();
            authorize.anyRequest().authenticated();
        })
                .formLogin(form -> form.loginPage("/login").permitAll())
                .logout(form -> form.invalidateHttpSession(true).clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());
        return httpSecurity.build();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}