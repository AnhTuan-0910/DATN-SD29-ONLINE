package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.DTO.UserRegistrationDto;
import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.repository.KhachHangRepository;
import com.springboot.bootstrap.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public KhachHang getOne(String id) {
        return khachHangRepository.findById(id).orElse(null);
    }

    @Override
    public void save(UserRegistrationDto registrationDto) {
        KhachHang khachHang = KhachHang.builder().ten(registrationDto.getFirstName()).email(registrationDto.getEmail()).matKhau(passwordEncoder.encode(registrationDto.getPassword())).build();
        khachHangRepository.save(khachHang);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        KhachHang khachHang = khachHangRepository.findByEmail(username);
        if(khachHang == null){
            throw new UsernameNotFoundException("Invalid username and password.");
        }
        Set<GrantedAuthority> listAuthorities = new HashSet<>();
        listAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(khachHang.getIdKhachHang(),khachHang.getMatKhau(),listAuthorities);
    }
}
