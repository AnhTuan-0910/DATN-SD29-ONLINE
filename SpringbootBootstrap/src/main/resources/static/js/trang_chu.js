$(document).ready(function () {

    finTop6SPBC();
    finTop6SPNew();


});

function finTop6SPBC() {
    var url = 'http://localhost:8080/spOnl/top6SPBC';
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
            if (data) {
                $('.pdBC').empty();


                $.each(data, function (index, sp) {
                    var maSP = sp.ma;
                    var idImg = 'imgSPBC' + maSP;

                    var minPrice = 0;
                    var maxPrice = 0;
                    $.ajax({
                        type: 'GET',
                        url: 'http://localhost:8080/spOnl/gia_min?id=' + sp.id,
                        success: function (dgmin) {
                            minPrice = dgmin;
                        }
                    });
                    $.ajax({
                        type: 'GET',
                        url: 'http://localhost:8080/spOnl/gia_max?id=' + sp.id,
                        success: function (dgmax) {
                            maxPrice = dgmax;
                        }
                    });
                    $.ajax({
                        type: 'GET',
                        url: 'http://localhost:8080/spOnl/spct?id=' + sp.id,
                        success: function (spctdata) {

                            $.ajax({
                                type: 'GET',
                                url: 'http://localhost:8080/spOnl/convertToBase64?id=' + spctdata[0].id,
                                success: function (response) {
                                    $('#imgSPBC' + maSP + '').attr('src', 'data:image/jpeg;base64,' + response)


                                },
                                error: function (xhr, status, error) {
                                    console.error('Error:', error);
                                }

                            });

                            $('#SPBC' + (index + 1)).append(`

        
            <figure class="product-media">
             <span class="product-label label-sale">Hot</span>
                <a href="/shop/detailSP/${sp.id}">
                    <img id="${idImg}" src="" alt="Product image"
                         class="product-image">
                </a>
                <div class="product-action">
                                    <a href="/shop/detailSP/${sp.id}" class="btn-product btn-cart"><span>Xem chi tiết</span></a>
                                </div>

            </figure>

            <div class="product-body">
                <h3 class="product-title"><a style="text-decoration: none" href="/shop/detailSP/${sp.id}">${sp.ten} ${sp.thuongHieu.ten}</a></h3>
                <div class="product-price">
                 ${minPrice}₫ - ${maxPrice}₫
                </div>
            </div>
        


                    `);


                        },
                        error: function (xhr, status, error) {
                            console.error('Error:', error);
                        }

                    });


                });
            } else {
                Swal.fire({
                    title: "Không tìm thấy sản phẩm ",
                    icon: "info",


                });
            }


        },

    });
}

function finTop6SPNew() {
    var url = 'http://localhost:8080/spOnl/top6SPNEW';
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
            if (data) {
                $('.pdNew').empty();


                $.each(data, function (index, sp) {
                    var maSP = sp.ma;
                    var idImg = 'imgSPNew' + maSP;

                    var minPrice = 0;
                    var maxPrice = 0;
                    $.ajax({
                        type: 'GET',
                        url: 'http://localhost:8080/spOnl/gia_min?id=' + sp.id,
                        success: function (dgmin) {
                            minPrice = dgmin;
                        }
                    });
                    $.ajax({
                        type: 'GET',
                        url: 'http://localhost:8080/spOnl/gia_max?id=' + sp.id,
                        success: function (dgmax) {
                            maxPrice = dgmax;
                        }
                    });
                    $.ajax({
                        type: 'GET',
                        url: 'http://localhost:8080/spOnl/spct?id=' + sp.id,
                        success: function (spctdata) {

                            $.ajax({
                                type: 'GET',
                                url: 'http://localhost:8080/spOnl/convertToBase64?id=' + spctdata[0].id,
                                success: function (response) {
                                    $('#imgSPNew' + maSP + '').attr('src', 'data:image/jpeg;base64,' + response)


                                },
                                error: function (xhr, status, error) {
                                    console.error('Error:', error);
                                }

                            });

                            $('#SPNew' + (index + 1)).append(`

        
            <figure class="product-media">
             <span class="product-label label-new">New</span>
                <a href="/shop/detailSP/${sp.id}">
                    <img id="${idImg}" src="" alt="Product image"
                         class="product-image">
                </a>
                 <div class="product-action">
                                    <a href="/shop/detailSP/${sp.id}" class="btn-product btn-cart">
                                    <span>Xem chi tiết</span></a>
                                </div>

            </figure>

            <div class="product-body">
                <h3 class="product-title"><a style="text-decoration: none" href="/shop/detailSP/${sp.id}">${sp.ten} ${sp.thuongHieu.ten}</a></h3>
                <div class="product-price">
                 ${minPrice}₫ - ${maxPrice}₫
                </div>
            </div>
        


                    `);


                        },
                        error: function (xhr, status, error) {
                            console.error('Error:', error);
                        }

                    });


                });
            } else {
                Swal.fire({
                    title: "Không tìm thấy sản phẩm ",
                    icon: "info",


                });
            }


        },

    });


}
