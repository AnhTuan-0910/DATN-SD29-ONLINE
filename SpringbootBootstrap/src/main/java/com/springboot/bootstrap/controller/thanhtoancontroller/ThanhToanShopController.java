package com.springboot.bootstrap.controller.thanhtoancontroller;

import com.springboot.bootstrap.entity.FormatHelper;
import com.springboot.bootstrap.entity.GioHang;
import com.springboot.bootstrap.entity.GioHangChiTiet;
import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.HoaDonChiTiet;
import com.springboot.bootstrap.entity.HoaDonTimeline;
import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.entity.SanPhamCT;
import com.springboot.bootstrap.repository.HoaDonTLRepo;
import com.springboot.bootstrap.service.GioHangChiTietService;
import com.springboot.bootstrap.service.GioHangService;
import com.springboot.bootstrap.service.HoaDonChiTietService;
import com.springboot.bootstrap.service.HoaDonService;
import com.springboot.bootstrap.service.KhachHangService;
import com.springboot.bootstrap.service.SanPhamCTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/thanh_toan")
public class ThanhToanShopController {
    @Autowired
    private GioHangChiTietService gioHangChiTietService;
    @Autowired
    private GioHangService gioHangService;
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    private SanPhamCTService sanPhamCTService;
    @Autowired
    private HoaDonTLRepo hoaDonTLRepo;
    @GetMapping("")
    public String view(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GioHang gioHang = gioHangService.getIdByIdKh(khachHangService.getOne(userDetails.getUsername()));
        List<GioHangChiTiet> list = gioHangChiTietService.getListGhct(gioHang);
        model.addAttribute("listGioHangCt", list);
        model.addAttribute("giohang",gioHang);
        model.addAttribute("formatHelper",new FormatHelper());
        model.addAttribute("kh",khachHangService.getOne(userDetails.getUsername()));
        return "/customer/thanh-toan";
    }
    @PostMapping("/create_hoa_don")
    public String create(@RequestParam("thanhPho") String thanhPho,
                         @RequestParam("quanHuyen") String quanHuyen,
                         @RequestParam("phuongXa") String phuongXa,
                         @RequestParam("diaChi") String diaChi,
                         @RequestParam("ghiChu") String ghiChu){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KhachHang khachHang = khachHangService.getOne(userDetails.getUsername());
        GioHang gioHang = gioHangService.getIdByIdKh(khachHang);
        List<GioHangChiTiet> list = gioHangChiTietService.getListGhct(gioHangService.getIdByIdKh(khachHang));
        UUID uuid = UUID.randomUUID();
        hoaDonService.add(HoaDon.builder().idHoaDon(uuid).khachHang(khachHang).phieuGiamGia(null).gia(gioHang.getThanhTien()).tinhTrang(1).thanhTien(0.0).thanhPho(thanhPho).quanHuyen(quanHuyen).phuongXa(phuongXa).diaChi(diaChi).ghiChu(ghiChu).hinhThuc(1).thanhTien(gioHang.getThanhTien()).taoLuc(LocalDateTime.now()).build());
        HoaDon hoaDon = hoaDonService.getOne(uuid);
        HoaDonTimeline hoaDonTimeline= HoaDonTimeline.builder()
                .hoaDon(hoaDon)
                .moTa(ghiChu)
                .nguoiTao(khachHang.getTen())
                .trangThai(hoaDon.getTinhTrang())
                .ngayTao(LocalDateTime.now()).build();
        hoaDonTLRepo.save(hoaDonTimeline);
        for(GioHangChiTiet gioHangChiTiet:list){
            hoaDonChiTietService.add(HoaDonChiTiet.builder().hoaDon(hoaDon).sanPhamChiTiet(gioHangChiTiet.getSanPhamCT()).gia(gioHangChiTiet.getSanPhamCT().getGia()*gioHangChiTiet.getSoLuong()).soLuong(gioHangChiTiet.getSoLuong()).build());
            SanPhamCT sanPhamCT = gioHangChiTiet.getSanPhamCT();
            sanPhamCT.setSl(sanPhamCT.getSl()-gioHangChiTiet.getSoLuong());
            sanPhamCTService.save(sanPhamCT);
        }
        gioHangChiTietService.deleteAllByGioHang(gioHang);
        gioHang.setThanhTien(0.0);
        gioHangService.update(gioHang);
        return "redirect:/thanh_toan";
    }
}
