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

    @Column(name = "ma")
    private String ma;

//    @NotEmpty(message = "Không Được Để Trống Tên")
    @Column(name = "ten")
    private String ten;

    @Column(name = "ngay_sinh")
    private java.sql.Date ngaySinh;

    @Column(name = "gioi_tinh")
    private String gioiTinh;

//    @NotEmpty(message = "Không Được Để Trống Email")
//    @Email(message = "Email Chưa Đúng Định Dạng")
    @Column(name = "email")
    private String email;

    @Column(name = "mat_khau")
//    @Length(min = 8, message = "*Mật khẩu phải có ít nhất 8 ký tự")
//    @NotEmpty(message = "*Không Được Để Trống Mật Khẩu")
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

//    @Pattern(message = "Nhập số Điện Thoại Chưa Đúng", regexp = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$")
//    @NotEmpty(message = "Không Được Để Trống Số Điện Thoại")
//    @Size(min = 10, message = "Số Điện Thoại Tối Thiểu 10 Số")
//    @Size(max = 10, message = "Số Điện Thoại Tối Đa 10 Số")
    @Column(name = "sdt")
    private String sdt;

    @Override
    public int hashCode() {
        return 42;
    }

    public static String generatePassword() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "@";

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        int length = 8;  // Độ dài mật khẩu mong muốn

        // Chọn ít nhất 1 ký tự đặc biệt và 1 số
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));

        // Độ dài còn lại để hoàn thành mật khẩu
        int remainingLength = length - 2;

        String allCharacters = upper + lower + digits + specialChars;

        for (int i = 0; i < remainingLength; i++) {
            int index = random.nextInt(allCharacters.length());
            password.append(allCharacters.charAt(index));
        }
        return password.toString();
    }

    private String generateMaKhachHang() {
        // Tạo một UUID mới
        UUID uuid = UUID.randomUUID();

        // Chuyển UUID thành chuỗi và loại bỏ các ký tự '-'
        String uuidString = uuid.toString().replace("-", "");

        // Lấy 6 ký tự đầu của chuỗi UUID
        return "KH" + uuidString.toUpperCase().substring(0, 9);
    }

    @PrePersist
    public void prePersist(){
        // Tạo mã ngẫu nhiên không trùng nhau
        if (ma == null) {
            ma = generateMaKhachHang();
        }
        // Tạo mật khẩu ngẫu nhiên nếu trường matKhau là null hoặc trống
        if (matKhau == null) {
            matKhau = generatePassword();
        }
    }
}
