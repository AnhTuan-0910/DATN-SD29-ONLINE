package com.springboot.bootstrap.controller;

import com.springboot.bootstrap.entity.DanhMuc;
import com.springboot.bootstrap.entity.KichThuoc;
import com.springboot.bootstrap.entity.MauSac;
import com.springboot.bootstrap.entity.SanPham;
import com.springboot.bootstrap.entity.SanPhamCT;
import com.springboot.bootstrap.entity.ThuongHieu;
import com.springboot.bootstrap.repository.SanPhamCTRepo;
import com.springboot.bootstrap.repository.SanPhamRepo;
import com.springboot.bootstrap.service.DanhMucService;
import com.springboot.bootstrap.service.KichThuocService;
import com.springboot.bootstrap.service.MauSacService;
import com.springboot.bootstrap.service.SanPhamCTService;
import com.springboot.bootstrap.service.SanPhamService;
import com.springboot.bootstrap.service.ThuongHieuService;
import com.springboot.bootstrap.utility.Base64Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/spOnl")
public class SanPhamOnlineController {
    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private SanPhamCTRepo sanPhamCTRepo;
    @Autowired
    private SanPhamRepo sanPhamRepo;
    @Autowired
    private DanhMucService danhMucService;
    @Autowired
    private SanPhamCTService sanPhamCTService;
    @Autowired
    private Base64Image base64Image;
    @Autowired
    private ThuongHieuService thuongHieuService;
    @Autowired
    private KichThuocService kichThuocService;
    @Autowired
    private MauSacService mauSacService;


    @GetMapping("/allSP")
    public String getAll(Model model) {
        List<KichThuoc> listKT = kichThuocService.findAllByTrangThai();
        List<DanhMuc> listDM = danhMucService.findAllByTrangThai();
        List<ThuongHieu> listTH = thuongHieuService.findAllByTrangThai();
        List<MauSac> listMS = mauSacService.findAllByTrangThai();
        model.addAttribute("listKT", listKT);
        model.addAttribute("listDM", listDM);
        model.addAttribute("listTH", listTH);
        model.addAttribute("listMS", listMS);


        return "/customer/tat-ca-san-pham";
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<Page<SanPham>> getAll(@RequestParam("p") Optional<Integer> p) {
        Page<SanPham> listSP = sanPhamService.getAllByTT(PageRequest.of(p.orElse(0), 9));

        return ResponseEntity.ok(listSP);
    }

    @GetMapping("/convertToBase64")
    @ResponseBody
    public String ViewImg(@RequestParam("id") String id) {
        SanPhamCT spct = sanPhamCTService.getOne(id);
        byte[] imageData = spct.getData();
        String base64Data = base64Image.bytesToBase64(imageData);
        return base64Data;
    }

    @GetMapping("/spct")
    @ResponseBody
    public ResponseEntity<List<SanPhamCT>> spct(@RequestParam("id") String id) {


        return ResponseEntity.ok(sanPhamCTService.findAllBySP(id));
    }

    @GetMapping("/gia_min")
    @ResponseBody
    public ResponseEntity<String> dgMin(@RequestParam("id") String id) {


        return ResponseEntity.ok(sanPhamCTRepo.giaMin(id));
    }

    @GetMapping("/gia_max")
    @ResponseBody
    public ResponseEntity<String> dgMax(@RequestParam("id") String id) {


        return ResponseEntity.ok(sanPhamCTRepo.giaMax(id));
    }

    @GetMapping("/filterByDM")
    @ResponseBody
    public ResponseEntity<Page<SanPham>> ftDM(@RequestParam("listDM") List<String> listDM, @RequestParam("p") Optional<Integer> p) {


        return ResponseEntity.ok(sanPhamRepo.findByTenDMs(listDM, PageRequest.of(p.orElse(0), 9)));
    }

    @GetMapping("/filterByTH")
    @ResponseBody
    public ResponseEntity<Page<SanPham>> ftTH(@RequestParam("listTH") List<String> listTH, @RequestParam("p") Optional<Integer> p) {


        return ResponseEntity.ok(sanPhamRepo.findByTenTHs(listTH, PageRequest.of(p.orElse(0), 9)));
    }

    @GetMapping("/filterByMS")
    @ResponseBody
    public ResponseEntity<Page<SanPham>> ftMS(@RequestParam("listMS") List<String> listMS, @RequestParam("p") Optional<Integer> p) {


        return ResponseEntity.ok(sanPhamRepo.findByTenMSs(listMS, PageRequest.of(p.orElse(0), 9)));
    }

    @GetMapping("/filterByKT")
    @ResponseBody
    public ResponseEntity<Page<SanPham>> ftKT(@RequestParam("listKT") List<String> listKT, @RequestParam("p") Optional<Integer> p) {


        return ResponseEntity.ok(sanPhamRepo.findByTenKTs(listKT, PageRequest.of(p.orElse(0), 9)));
    }

    @GetMapping("/top6SPBC")
    @ResponseBody
    public ResponseEntity<List<SanPham>> spbc() {


        return ResponseEntity.ok(sanPhamRepo.findTop6SanPhamBanChay());
    }
    @GetMapping("/top6SPNEW")
    @ResponseBody
    public ResponseEntity<List<SanPham>> spnew() {


        return ResponseEntity.ok(sanPhamRepo.findTop6SanPhamMoiNhat());
    }
}


