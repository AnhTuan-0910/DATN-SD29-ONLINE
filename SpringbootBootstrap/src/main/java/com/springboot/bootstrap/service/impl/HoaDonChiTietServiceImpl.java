package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.HoaDonChiTiet;
import com.springboot.bootstrap.repository.HoaDonChiTietRepository;
import com.springboot.bootstrap.repository.HoaDonRepository;
import com.springboot.bootstrap.repository.SanPhamCTRepo;
import com.springboot.bootstrap.service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService {
    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;
    @Autowired
    private SanPhamCTRepo sanPhamCTRepo;
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public void add(HoaDonChiTiet hoaDonChiTiet) {
        hoaDonChiTietRepository.save(hoaDonChiTiet);
    }
    }

