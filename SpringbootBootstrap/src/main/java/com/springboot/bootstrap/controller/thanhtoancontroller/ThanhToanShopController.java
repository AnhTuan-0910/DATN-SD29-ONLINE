package com.springboot.bootstrap.controller.thanhtoancontroller;

import com.springboot.bootstrap.entity.GioHang;
import com.springboot.bootstrap.entity.GioHangChiTiet;
import com.springboot.bootstrap.service.GioHangChiTietService;
import com.springboot.bootstrap.service.GioHangService;
import com.springboot.bootstrap.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/thanh_toan")
public class ThanhToanShopController {
    @Autowired
    private GioHangChiTietService gioHangChiTietService;
    @Autowired
    private GioHangService gioHangService;
    @Autowired
    private KhachHangService khachHangService;
    @GetMapping("")
    public String view(Model model, @PathVariable(name = "idkh",value = "") String idkh){
        GioHang gioHang = gioHangService.getIdByIdKh(khachHangService.getOne(idkh));
        List<GioHangChiTiet> list = gioHangChiTietService.getListGhct(gioHang);
        model.addAttribute("listGioHangCt", list);
        model.addAttribute("giohang",gioHang);
        return "/customer/thanh-toan";
    }
}
