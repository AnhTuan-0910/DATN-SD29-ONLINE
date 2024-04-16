package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.HoaDon;

import java.util.UUID;

public interface HoaDonService {
    void add(HoaDon hoaDon);

    HoaDon getOne(UUID fromString);
}
