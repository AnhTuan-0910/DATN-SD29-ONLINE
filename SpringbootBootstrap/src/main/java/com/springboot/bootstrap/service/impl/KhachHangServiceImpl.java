package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.repository.KhachHangRepository;
import com.springboot.bootstrap.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;


    @Override
    public KhachHang getOne(String id) {
        return khachHangRepository.findById(id).orElse(null);
    }

}
