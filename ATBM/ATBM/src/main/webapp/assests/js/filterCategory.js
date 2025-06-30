// LẤY BIẾN contextPath TỪ SERVER (GÁN TỪ JSP)
const contextPath = window.contextPath || '';

// GỌI API ĐỂ LỌC SẢN PHẨM
async function applyFilters() {
    const data = $('#filterForm').serialize();
    $('#loading-spinner').show();

    try {
        const response = await $.ajax({
            url: `${contextPath}/shop/product`,
            method: 'POST',
            data: data,
            dataType: 'json'
        });

        const productList = document.getElementById('product-list');
        productList.innerHTML = '';

        if (!response || response.length === 0) {
            productList.innerHTML = `
                <div class="col-12">
                    <p class="text-center">Không tìm thấy sản phẩm nào.</p>
                </div>`;
            return;
        }

        response.forEach(product => {
            const productCard = document.createElement('div');
            productCard.className = 'col-md-6 col-lg-4';

            productCard.innerHTML = `
                <div class="card text-center card-product">
                    <div class="card-product__img">
                        <div class="product-image-wrapper"
                             style="width:250px; height:250px; display:flex; justify-content:center; align-items:center; overflow:hidden;">
                            <img class="product-image"
                              src="${contextPath}/product-image/${product.productId}"
                                 alt="${product.name}"
                                 style="width:100%; height:100%; object-fit:cover;"
                                 onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';" />
                            <div class="product-placeholder"
                                 style="display:none; align-items:center; justify-content:center; background-color:#667eea; width:100%; height:100%;">
                                <i class="fas fa-image" style="font-size:32px; color:#ccc;"></i>
                            </div>
                        </div>
                        <ul class="card-product__imgOverlay">
                            <!-- MỞ COMMENT NẾU CẦN ICON -->
                            <!--
                            <li><button><i class="ti-search"></i></button></li>
                            <li><button><i class="ti-shopping-cart"></i></button></li>
                            <li><button><i class="ti-heart"></i></button></li>
                            -->
                        </ul>
                    </div>
                    <div class="card-body">
                        <h4 class="card-product__title">
                            <a href="${contextPath}/product?action=details&id=${product.productId}">
                                ${product.name}
                            </a>
                        </h4>
                        <p>${product.description}</p>
                        <p class="card-product__price">${product.price != null ? product.price + ' ₫' : '0 ₫'}</p>
                    </div>
                </div>
            `;
            productList.appendChild(productCard);
        });

    } catch (error) {
        console.error("LỖI KHI ÁP DỤNG BỘ LỌC", error);
    } finally {
        $('#loading-spinner').hide();
    }
}

// KHI DOM SẴN SÀNG
$(function () {
    const slider = document.getElementById('price-range');
    if (slider) {
        // KHỞI TẠO THANH KÉO GIÁ
        noUiSlider.create(slider, {
            start: [
                parseInt($('#minPriceInput').val()) || 0,
                parseInt($('#maxPriceInput').val()) || 1000
            ],
            range: {
                min: parseInt($('#minPriceInput').attr('min')) || 0,
                max: parseInt($('#maxPriceInput').attr('max')) || 1000
            }
        });

        // CẬP NHẬT TEXT GIÁ KHI KÉO
        slider.noUiSlider.on('update', function (values) {
            $('#lower-value').text(Math.round(values[0]));
            $('#upper-value').text(Math.round(values[1]));
        });

        // GÁN GIÁ TRỊ MỚI KHI THAY ĐỔI VÀ ÁP DỤNG LỌC
        slider.noUiSlider.on('change', function (values) {
            $('#minPriceInput').val(Math.round(values[0]));
            $('#maxPriceInput').val(Math.round(values[1]));
            applyFilters();
        });
    }

    // GỌI APPLY FILTER KHI TICK CHECKBOX
    $('#filterForm input[type="checkbox"]').on('change', applyFilters);
});
