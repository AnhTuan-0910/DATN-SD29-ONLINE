package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.KhachHang;
import org.springframework.data.domain.Page;

import java.util.List;

public interface KhachHangService {
    KhachHang getOne(String id);
}
