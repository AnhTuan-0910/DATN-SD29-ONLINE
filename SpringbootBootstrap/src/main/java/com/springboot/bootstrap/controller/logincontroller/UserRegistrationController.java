package com.springboot.bootstrap.controller.logincontroller;

import com.springboot.bootstrap.entity.DTO.UserRegistrationDto;
import com.springboot.bootstrap.service.KhachHangService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    private KhachHangService khachHangService;

    public UserRegistrationController(KhachHangService userService) {
        super();
        this.khachHangService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "/customer/registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        khachHangService.save(registrationDto);
        return "redirect:/registration?success";
    }
}
