package com.springboot.bootstrap.controller;

import com.springboot.bootstrap.entity.DTO.GioHangAddDTO;
import com.springboot.bootstrap.entity.GioHang;
import com.springboot.bootstrap.entity.GioHangChiTiet;
import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.entity.KichThuoc;
import com.springboot.bootstrap.entity.MauSac;
import com.springboot.bootstrap.entity.SanPham;
import com.springboot.bootstrap.entity.SanPhamCT;
import com.springboot.bootstrap.repository.GioHangChiTietRepository;
import com.springboot.bootstrap.repository.GioHangRepository;
import com.springboot.bootstrap.repository.SanPhamCTRepo;
import com.springboot.bootstrap.service.KhachHangService;
import com.springboot.bootstrap.service.SanPhamCTService;
import com.springboot.bootstrap.service.SanPhamService;
import com.springboot.bootstrap.utility.Base64Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/shop")
public class SanPhamDetailOnlineController {
    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private SanPhamCTRepo sanPhamCTRepo;


    @Autowired
    private SanPhamCTService sanPhamCTService;
    @Autowired
    private Base64Image base64Image;

    @Autowired
    private GioHangRepository gioHangRepo;
    @Autowired
    private GioHangChiTietRepository gioHangCTRepo;
    @Autowired
    private KhachHangService khachHangService;

    @GetMapping("/detailSP/{idSP}")
    public String getSPDetail(@PathVariable("idSP") String idSP, Model model) {

        model.addAttribute("idSP", idSP);
        return "/customer/detailSP";
    }


    @GetMapping("/convertToBase64")
    @ResponseBody
    public String spctImg(@RequestParam("id") String id) {
        SanPhamCT spct = sanPhamCTService.getOne(id);
        byte[] imageData = spct.getData();
        String base64Data = base64Image.bytesToBase64(imageData);
        return base64Data;
    }

    @GetMapping("/spct")
    @ResponseBody
    public ResponseEntity<Map<String, List<SanPhamCT>>> spct(@RequestParam("id") String id) {
        Map<String, List<SanPhamCT>> responseData = new HashMap<>();
        responseData.put("content", sanPhamCTService.findAllBySP(id));
        return ResponseEntity.ok(responseData);
    }


    @GetMapping("/mauSP")
    @ResponseBody
    public ResponseEntity<List<MauSac>> mauSP(@RequestParam("id") String id) {


        return ResponseEntity.ok(sanPhamCTRepo.mauSP(id));
    }

    @GetMapping("/ktSPCT")
    @ResponseBody
    public ResponseEntity<List<SanPhamCT>> sizeSPCT(@RequestParam("id") String id, @RequestParam("idMS") String idMS) {


        return ResponseEntity.ok(sanPhamCTRepo.findKTByMS(id, idMS));
    }


    @GetMapping("/ktSP")
    @ResponseBody
    public ResponseEntity<List<KichThuoc>> sizeSP(@RequestParam("id") String id) {


        return ResponseEntity.ok(sanPhamCTRepo.sizeSP(id));
    }

    @GetMapping("/sp/")
    @ResponseBody
    public ResponseEntity<SanPham> getOneSP(@RequestParam("id") String id) {


        return ResponseEntity.ok(sanPhamService.detail(id));
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

    @GetMapping("/user/spctGH")
    @ResponseBody
    public ResponseEntity<SanPhamCT> spctAddGH(@RequestParam("id") String id, @RequestParam("idMS") String idMS, @RequestParam("idKT") String idKT) {


        return ResponseEntity.ok(sanPhamCTRepo.findSPCTByKTAndMS(id, idMS, idKT));
    }

    @GetMapping("/user/findAllGHCT")
    @ResponseBody
    public ResponseEntity<Page<GioHangChiTiet>> spct(@RequestParam("p") Optional<Integer> p) {
        String idKH = "CF0A193C-B149-4F91-8A4A-1BC84237F155";
        KhachHang khachHang = khachHangService.getOne(idKH);
        Page<GioHangChiTiet> listGHCT = gioHangCTRepo.findAllByKH(khachHang, PageRequest.of(p.orElse(0), 3));
        return ResponseEntity.ok(listGHCT);
    }

    @GetMapping("/user/getAllGHCT")
    @ResponseBody
    public ResponseEntity<List<GioHangChiTiet>> allGHCTList() {
        String idKH = "CF0A193C-B149-4F91-8A4A-1BC84237F155";
        KhachHang khachHang = khachHangService.getOne(idKH);
        List<GioHangChiTiet> listGHCT = gioHangCTRepo.getAllByKhachHang(khachHang);
        return ResponseEntity.ok(listGHCT);
    }

    @GetMapping("/user/getGHCTBySPCT")
    @ResponseBody
    public ResponseEntity<GioHangChiTiet> validateSL(@RequestParam("idSPCT") String idSPCT) {
        String idKH = "CF0A193C-B149-4F91-8A4A-1BC84237F155";
        KhachHang khachHang = khachHangService.getOne(idKH);
        GioHangChiTiet ghct = gioHangCTRepo.getBySPCT(idSPCT, khachHang);
        return ResponseEntity.ok(ghct);
    }

    @PostMapping("/user/addGH")
    public ResponseEntity<Map<String, String>> addSanPham(@RequestBody GioHangAddDTO gioHangAddDTO) {
        String idKH = "CF0A193C-B149-4F91-8A4A-1BC84237F155";
        KhachHang khachHang = khachHangService.getOne(idKH);
        GioHang gh = gioHangRepo.findByKhachHang(khachHang);

        SanPhamCT sanPhamCT = sanPhamCTRepo.findById(gioHangAddDTO.getIdSPCT()).get();

        boolean isUpdated = false;
        List<GioHangChiTiet> listGHCT = gioHangCTRepo.getAllByKhachHang(khachHang);
        if (listGHCT != null) {
            for (GioHangChiTiet ghctUp : listGHCT) {
                if (ghctUp.getSanPhamCT().getId().equals(sanPhamCT.getId())) {
                    ghctUp.setSoLuong(ghctUp.getSoLuong() + gioHangAddDTO.getSoLuong());
                    ghctUp.setDonGia(ghctUp.getSoLuong() * sanPhamCT.getGia());
                    gioHangCTRepo.save(ghctUp);
                    isUpdated = true;
                    break;

                }


            }

        }
        if (!isUpdated) {
            GioHangChiTiet ghct = GioHangChiTiet.builder()
                    .gioHang(gh)
                    .sanPhamCT(sanPhamCT)
                    .soLuong(gioHangAddDTO.getSoLuong())
                    .donGia(sanPhamCT.getGia() * gioHangAddDTO.getSoLuong()).build();
            gioHangCTRepo.save(ghct);
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Success");
        return ResponseEntity.ok(response);

    }
}


