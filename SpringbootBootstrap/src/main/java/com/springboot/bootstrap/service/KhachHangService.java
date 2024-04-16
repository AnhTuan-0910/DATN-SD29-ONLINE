package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.DTO.UserRegistrationDto;
import com.springboot.bootstrap.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface KhachHangService extends UserDetailsService {
    KhachHang getOne(String id);

    void save(UserRegistrationDto registrationDto);
}
