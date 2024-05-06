package com.springboot.bootstrap.controller.hoadoncontroller;

import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.HoaDonChiTiet;
import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.service.HoaDonChiTietService;
import com.springboot.bootstrap.service.HoaDonService;
import com.springboot.bootstrap.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/hoa_don")
public class HoaDonController {
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;
    @GetMapping("/getListHoaDon")
    public String getListHoaDon(Model model, @RequestParam("p") Optional<Integer> p){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KhachHang khachHang = khachHangService.getOne(userDetails.getUsername());
        List<HoaDon> list = hoaDonService.getListHoaDon(khachHang);
        Page<HoaDon> page = new PageImpl(list, PageRequest.of(p.orElse(0), 5), list.size());
        model.addAttribute("listHoaDon",page);
        return "/customer/danh-sach-hoa-don";
    }
    @GetMapping("/view/{id}")
    public String viewOne(Model model,@RequestParam("p") Optional<Integer> p, @PathVariable("id") UUID id){
        model.addAttribute("hoaDon",hoaDonService.getOne(id));
        List<HoaDonChiTiet> list = hoaDonChiTietService.getListHoaDonChiTiet(hoaDonService.getOne(id));
        Page<HoaDonChiTiet> page = new PageImpl(list,PageRequest.of(p.orElse(0), 5),list.size());
        model.addAttribute("listHoaDonChiTiet",page);
        return "/customer/hoa-don-chi-tiet";
    }
    @GetMapping("/search")
    public String search(@RequestParam("p") Optional<Integer> p,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KhachHang khachHang = khachHangService.getOne(userDetails.getUsername());
        List<HoaDon> list = hoaDonService.getListSearch(keyword,khachHang);
        Page<HoaDon> hoaDonPage = new PageImpl(list,PageRequest.of(p.orElse(0), 5), list.size());
        model.addAttribute("listHoaDon",hoaDonPage);
        return "/pages/hoa_don";
    }
    @PostMapping("/xac_nhan")
    public String xacNhan(@RequestParam("ghiChu") String ghiChu,
                          @RequestParam("idHoaDon") UUID idHoaDon){
        HoaDon hoaDon = hoaDonService.getOne(idHoaDon);
        hoaDon.setTinhTrang(4);
        hoaDon.setGhiChu(ghiChu);
        hoaDonService.save(hoaDon);
        return "redirect:/hoa_don/view/"+idHoaDon;
    }
    @PostMapping("/huy")
    public String huy(@RequestParam("ghiChuHuy") String ghiChu,
                      @RequestParam("idHoaDon") UUID idHoaDon){
        HoaDon hoaDon = hoaDonService.getOne(idHoaDon);
        hoaDon.setTinhTrang(5);
        hoaDon.setGhiChu(ghiChu);
        hoaDonService.save(hoaDon);
        return "redirect:/";
    }
}
