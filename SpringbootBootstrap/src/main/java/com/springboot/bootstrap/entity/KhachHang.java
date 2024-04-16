package com.springboot.bootstrap.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.security.SecureRandom;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "khach_hang")
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_kh")
    private String idKhachHang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dia_chi", referencedColumnName = "id_dia_chi")
    private DiaChi idDiaChi;

    @Column(name = "ten")
    private String ten;

    @Column(name = "ngay_sinh")
    private java.sql.Date ngaySinh;

    @Column(name = "gioi_tinh")
    private String gioiTinh;

    @Column(name = "email")
    private String email;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "anh_nhan_vien")
    private String anhNhanVien;

    @Column(name = "trang_thai")
    private int trangThai;

    @Column(name = "tao_luc")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.sql.Timestamp taoLuc;

    @Column(name = "sua_luc")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.sql.Timestamp suaLuc;

    @Column(name = "tao_boi")
    private String taoBoi;

    @Column(name = "sua_boi")
    private String suaBoi;

    @Column(name = "sdt")
    private String sdt;

}
