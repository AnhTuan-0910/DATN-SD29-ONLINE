package com.springboot.bootstrap.controller.thanhtoancontroller;

import com.springboot.bootstrap.entity.DTO.ValidateDTO;
import com.springboot.bootstrap.entity.GioHang;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/thanh_toan")
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
    public ValidateDTO validate(@RequestBody HoaDon hdc){
        if(hdc.getThanhPho().isEmpty()||hdc.getQuanHuyen().isEmpty()||hdc.getPhuongXa().isEmpty()||hdc.getDiaChi().isEmpty()){
            return ValidateDTO.builder().success(false).message("Vui lòng nhập đầy đủ dữ liệu" ).build();
        }
        Double sumMoney=0.0;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KhachHang khachHang = khachHangService.getOne(userDetails.getUsername());
        GioHang gioHang = gioHangService.getIdByIdKh(khachHang);
        List<GioHangChiTiet> list = gioHangChiTietService.getListGhct(gioHangService.getIdByIdKh(khachHang));
        for(GioHangChiTiet gioHangChiTiet:list){
            SanPhamCT sanPhamCT = gioHangChiTiet.getSanPhamCT();
            if(sanPhamCT.getSl()<gioHangChiTiet.getSoLuong()){
                return ValidateDTO.builder().success(false).message("Sản phẩm " +sanPhamCT.getMa()+" bị vượt quá số lượng, vui lòng thử lại" ).build();
            }else {
                sumMoney+=gioHangChiTiet.getSoLuong()*sanPhamCT.getGia();
            }
        }
        if(gioHang.getThanhTien()!=sumMoney){
            return ValidateDTO.builder().success(false).message("Có sản phẩm đã bị thay đổi giá, vui lòng thử lại" ).build();
        }
        return ValidateDTO.builder().success(true).build();
    }
}
