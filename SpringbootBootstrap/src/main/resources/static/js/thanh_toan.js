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
})