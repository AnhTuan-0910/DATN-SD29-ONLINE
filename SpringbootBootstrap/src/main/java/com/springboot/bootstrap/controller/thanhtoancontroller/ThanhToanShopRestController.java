package com.springboot.bootstrap.controller.thanhtoancontroller;

import com.springboot.bootstrap.entity.GioHangChiTiet;
import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.HoaDonChiTiet;
import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.entity.SanPhamCT;
import com.springboot.bootstrap.service.GioHangChiTietService;
import com.springboot.bootstrap.service.GioHangService;
import com.springboot.bootstrap.service.HoaDonChiTietService;
import com.springboot.bootstrap.service.HoaDonService;
import com.springboot.bootstrap.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/thanh-toan")
public class ThanhToanShopRestController {
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private GioHangService gioHangService;
    @Autowired
    private GioHangChiTietService gioHangChiTietService;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    private HoaDonService hoaDonService;
    @PostMapping("/validateThanhToan")
    public String validate(@RequestBody String idkh){
        KhachHang khachHang = khachHangService.getOne(idkh);
        List<GioHangChiTiet> list = gioHangChiTietService.getListGhct(gioHangService.getIdByIdKh(khachHang));
        for(GioHangChiTiet gioHangChiTiet:list){
            SanPhamCT sanPhamCT = gioHangChiTiet.getSanPhamCT();
            if(sanPhamCT.getSl()<gioHangChiTiet.getSoLuong()){
                return "that bai";
            }
        }
        UUID uuid = UUID.randomUUID();
        hoaDonService.add(HoaDon.builder().idHoaDon(uuid).khachHang(null).phieuGiamGia(null).gia(null).tinhTrang(1).thanhTien(null).build());
        HoaDon hoaDon = hoaDonService.getOne(uuid);
        for(GioHangChiTiet gioHangChiTiet:list){
            hoaDonChiTietService.add(HoaDonChiTiet.builder().hoaDon(hoaDon).sanPhamChiTiet(gioHangChiTiet.getSanPhamCT()).gia(gioHangChiTiet.getDonGia()*gioHangChiTiet.getSoLuong()).soLuong(gioHangChiTiet.getSoLuong()).build());
        }
        gioHangChiTietService.deleteAllByGioHang(gioHangService.getIdByIdKh(khachHang));
        return "thanh cong";
    }
}
