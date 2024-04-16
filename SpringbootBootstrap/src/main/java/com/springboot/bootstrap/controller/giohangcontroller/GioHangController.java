package com.springboot.bootstrap.controller.giohangcontroller;


import com.springboot.bootstrap.entity.FormatHelper;
import com.springboot.bootstrap.entity.GioHang;
import com.springboot.bootstrap.entity.GioHangChiTiet;
import com.springboot.bootstrap.repository.GioHangChiTietRepository;
import com.springboot.bootstrap.repository.GioHangRepository;
import com.springboot.bootstrap.utility.Base64Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/shop/gio-hang")
public class GioHangController{

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private Base64Image base64Image;

    @GetMapping
    public String getAll(Model model){
        List<GioHangChiTiet> listGioHang=gioHangChiTietRepository.findAllByGioHang_KhachHang_Ma("KH005");
        model.addAttribute("listGioHangCT",listGioHang);
        GioHang gioHang=gioHangRepository.findAllByKhachHang_Ma("KH005");
        model.addAttribute("gioHang",gioHang);
        model.addAttribute("formatHelper", new FormatHelper());
        model.addAttribute("base64Image", base64Image);
        return "/customer/gio-hang";
    }

    @PostMapping("/update-so-luong/{idGhct}")
//    @ResponseBody
    public String updateSoLuong(@PathVariable(value = "idGhct", required = false) UUID idGhct
                                ,@RequestParam(name = "soLuong") int soLuong) {
            GioHangChiTiet gioHangChiTiet=gioHangChiTietRepository.findById(idGhct).orElse(null);
            gioHangChiTiet.setSoLuong(soLuong);
            gioHangChiTiet.setDonGia(gioHangChiTiet.getSanPhamCT().getGia()*gioHangChiTiet.getSoLuong());
            gioHangChiTietRepository.save(gioHangChiTiet);
        return "redirect:/shop/gio-hang";
    }

    @GetMapping("/delete/{idGhct}")
    public String deleteItem(Model model,
                             @PathVariable("idGhct") UUID idGhct) {
        gioHangChiTietRepository.deleteById(idGhct);
        return "redirect:/shop/gio-hang";
    }


}

