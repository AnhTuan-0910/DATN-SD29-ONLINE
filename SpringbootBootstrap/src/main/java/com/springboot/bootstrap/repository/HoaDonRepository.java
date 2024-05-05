package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, UUID> {
    HoaDon findByIdHoaDon(UUID uuid);
    List<HoaDon> findAllByKhachHang(KhachHang khachHang);
    List<HoaDon> findAllByKhachHangAndMaContaining(KhachHang khachHang,String keyword);
}
