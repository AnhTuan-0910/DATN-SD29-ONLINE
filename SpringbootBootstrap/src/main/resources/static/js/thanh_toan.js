$(document).ready(function () {
    $.ajax({
        type : "GET",
        url : 'https://vapi.vnappmob.com/api/province',
        success : function (data){
            data.results.forEach((elem) => {
                $('#thanhPho').append(`<option value=${elem.province_id}>${elem.province_name}</option>`);
            })
        },
        error : function (error){
            console.log(error);
        }
    })
    $('#thanhPho').on("change",function (){
        const id = $('#thanhPho').val();
        $.ajax({
            type:"GET",
            url:`https://vapi.vnappmob.com/api/province/district/`+id,
            success : function (data){
                $('#quanHuyen').empty();
                data.results.forEach((elem) => {
                    $('#quanHuyen').append(`<option value=${elem.district_id}>${elem.district_name}</option>`)
                })
            },
            error : function (error) {
                console.log(error);
            }
        })
    })
    $('#quanHuyen').on("change",function (){
        const id = $('#quanHuyen').val();
        $.ajax({
            type:"GET",
            url:`https://vapi.vnappmob.com/api/province/ward/`+id,
            success : function (data){
                $('#phuongXa').empty();
                data.results.forEach((elem) => {
                    $('#phuongXa').append(`<option value=${elem.ward_id}>${elem.ward_name}</option>`)
                })
            },
            error : function (error) {
                console.log(error);
            }
        })
    })
    $("#thanhToan").on("click",function (e){
        e.preventDefault();
        $.ajax({
            type:"POST",
            url:"/thanh_toan/validateThanhToan",
            processData: false,
            contentType: "application/json",
            data : JSON.stringify({
                thanhPho : $("#thanhPho").val(),
                quanHuyen : $('#quanHuyen').val(),
                phuongXa : $('#phuongXa').val(),
                diaChi : $('#diaChi').val()
            }),
            dataType: "json",
            success:function (data){
                if(data.success){
                    $('#formThanhToan').submit();
                }else {
                    Swal.fire({
                        icon:"error",
                        title:"Error",
                        text:data.message
                    })
                }
            },
            error:function (e){
                console.log(e);
            }
        })
    })
    $('#saveChangesBtn').on('click', function() {
        // Lấy giá trị của radio button đã chọn
        var maVoucher = $('input[name="maVoucher"]:checked').val();
        console.log(maVoucher);
        $('#voucher').val(maVoucher)
        $('#voucher-in-form').val(maVoucher)
        // Đặt giá trị đó vào trường input voucher trên trang chính
        var api="http://localhost:8081/thanh_toan/select-voucher"
        fetch(`${api}?maVoucher=${maVoucher}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                var donVi = data.donVi;
                var giaTriGiam = data.giaTriGiam;
                var giaTriGiamToiDa=data.giaTriGiamToiDa;
                var gia = parseFloat(document.getElementById('gia').innerText);

                // Tính giá trị giảm giá và cập nhật HTML
                var giamGia;
                var thanhTien;
                if (donVi === 1) {
                    giamGia = (gia * giaTriGiam/100);
                    if (giamGia>=giaTriGiamToiDa){
                        giamGia=giaTriGiamToiDa;
                    }
                } else if (donVi === 2) {
                    giamGia = giaTriGiam;
                }
                thanhTien=gia-giamGia;
                // Cập nhật nội dung HTML
                document.getElementById('giamGia').innerText ='-' + giamGia;
                document.getElementById('thanhTien').innerText= thanhTien;
            })
            .catch(error => {
            console.error('Error:', error);
        });
    });
    // $('#voucher').on('change', function() {
    //     //lấy giá trị nhập vào input
    //     const maVoucher = $('#voucher').val().trim().toLowerCase();
    //     console.log(maVoucher);
    //     if (maVoucher!=null){
    //         // $('#voucher-in-form').val(maVoucher);
    //         // Đặt giá trị đó vào trường input voucher trên trang chính
    //             var api="http://localhost:8081/thanh_toan/select-voucher"
    //             fetch(`${api}?maVoucher=${maVoucher}`)
    //                 .then(response => response.json())
    //                 .then(data => {
    //                     if (data!=null){
    //                         console.log(data);
    //                         var donVi = data.donVi;
    //                         var giaTriGiam = data.giaTriGiam;
    //                         var giaTriGiamToiDa=data.giaTriGiamToiDa;
    //                         var gia = parseFloat(document.getElementById('gia').innerText);
    //
    //                         // Tính giá trị giảm giá và cập nhật HTML
    //                         var giamGia;
    //                         var thanhTien;
    //                         if (donVi === 1) {
    //                             giamGia = (gia * giaTriGiam/100);
    //                             if (giamGia>=giaTriGiamToiDa){
    //                                 giamGia=giaTriGiamToiDa;
    //                             }
    //                         } else if (donVi === 2) {
    //                             giamGia = giaTriGiam;
    //                         }
    //                         thanhTien=gia-giamGia;
    //                         // Cập nhật nội dung HTML
    //                         document.getElementById('giamGia').innerText ='-' + giamGia;
    //                         document.getElementById('thanhTien').innerText= thanhTien;
    //                     }
    //                 })
    //             }
    //         })
    //         .catch(error => {
    //             console.error('Error:', error);
    //         });
    // });
    const searchInputVoucher = document.getElementById("searchInputVoucher");
    const vouchers = Array.from(document.getElementsByClassName("card-voucher"));
    searchInputVoucher.addEventListener("input", function () {
        const searchTerm = searchInputVoucher.value.trim().toLowerCase();
        vouchers.forEach(function (voucher) {
            const voucherCardId = voucher.id;
            const maVoucher = document.querySelector("#" + voucherCardId + " .modal-maVoucher").textContent.trim().toLowerCase();
            const tenVoucher = document.querySelector("#" + voucherCardId + " .modal-tenVoucher").textContent.trim().toLowerCase();
            // const description = voucher.querySelector(".voucher-description").textContent.trim().toLowerCase();
            const isVisible = maVoucher.includes(searchTerm) || tenVoucher.includes(searchTerm);

            if (isVisible) {
                voucher.style.display = "block";
            } else {
                voucher.style.display = "none";
            }
        });
    })
})

