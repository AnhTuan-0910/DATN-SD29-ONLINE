package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.repository.HoaDonRepository;
import com.springboot.bootstrap.repository.KhachHangRepository;
import com.springboot.bootstrap.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;




}
