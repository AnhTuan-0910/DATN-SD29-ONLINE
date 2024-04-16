package com.springboot.bootstrap.entity.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SanPhamDTO {
    private String idSP;
    private String ten;
    private String trangThai;
    private String thuongHieu;
    private String danhMuc;
    private String[] idMSAr;
    private String[] idKTAr;
}
