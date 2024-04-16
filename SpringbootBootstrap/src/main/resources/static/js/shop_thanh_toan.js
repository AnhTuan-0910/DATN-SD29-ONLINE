$(document).ready(function () {
    $("#thanhToan").on("click",function (e){
        e.preventDefault();
        let idKhachHang = localStorage.getItem("idkh");
        $.ajax({
            type:"POST",
            url:"/validateThanhToan",
            processData: false,
            data:JSON.stringify(idKhachHang),
            contentType: "application/json",
            dataType: "json",
            success:function (data){
                if(data.status==200){
                    console.log("heloo")
                    window.location.href="/shop/thanh-toan";
                }else {
                    Swal.fire({
                        icon:"error",
                        title:"Error",
                        text:"Sản phẩm"+data.idspct+"có"+data.soLuong+" sản phẩm",
                    })
                }
            },
            error:function (e){
                console.log(e);
            }
        })
    })
})