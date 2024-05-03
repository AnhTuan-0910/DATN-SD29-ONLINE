$(document).ready(function () {
    $.ajax({
        type : "GET",
        url : 'https://vnprovinces.pythonanywhere.com/api/provinces',
        success : function (data){
            data.results.forEach((elem) => {
                $('#thanhPho').append(`<option value=${elem.id}>${elem.name}</option>`);
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
            url:`https://vnprovinces.pythonanywhere.com/api/provinces/`+id,
            success : function (data){
                data.districts.forEach((elem) => {
                    $('#quanHuyen').append(`<option value=${elem.id}>${elem.name}</option>`)
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
            url:`https://vnprovinces.pythonanywhere.com/api/districts/`+id,
            success : function (data){
                data.wards.forEach((elem) => {
                    $('#phuongXa').append(`<option value=${elem.id}>${elem.name}</option>`)
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
                if(data.status==200){
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
})