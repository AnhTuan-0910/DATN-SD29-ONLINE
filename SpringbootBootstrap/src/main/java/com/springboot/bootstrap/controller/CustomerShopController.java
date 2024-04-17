package com.springboot.bootstrap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class CustomerShopController {

    @RequestMapping("")
    public String index() {
        return "/customer/index";
    }



    @RequestMapping("/danh-muc-giay")
    public String danhMucSanPham() {
        return "/customer/danh-muc-san-pham";
    }

    @RequestMapping("/detailSP")
    public String detailSP() {
        return "/customer/detailSP";
    }

    @RequestMapping("/gio-hang")
    public String gioHang() {
        return "/customer/gio-hang";
    }

    @RequestMapping("/voucher")
    public String voucher() {
        return "/customer/voucher";
    }

    @RequestMapping("/about")
    public String about() {
        return "/customer/about";
    }

    @RequestMapping("/thanh-toan")
    public String thanhToan(){return "/customer/thanh-toan";}
}
