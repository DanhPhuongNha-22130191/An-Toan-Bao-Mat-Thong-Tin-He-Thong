// LẤY BIẾN contextPath TỪ SERVER (GÁN TỪ JSP)
const contextPath = window.contextPath || '';

// HÀM ÁP DỤNG BỘ LỌC SẢN PHẨM
async function applyFilters() {
    // LẤY DỮ LIỆU TỪ FORM
    const data = $('#filterForm').serialize();
    $('#loading-spinner').show(); // HIỆN SPINNER ĐANG TẢI

    try {
        // GỌI API ĐỂ LỌC SẢN PHẨM
        const response = await $.ajax({
            url: `${contextPath}/product/filter`,
            method: 'GET',
            data: data,
            dataType: 'json'
        });

        const productList = document.getElementById('product-list');
        productList.innerHTML = ''; // XOÁ DANH SÁCH CŨ

        // NẾU KHÔNG CÓ SẢN PHẨM
        if (response.length === 0) {
            const noProductsMessage = document.createElement('div');
            noProductsMessage.classList.add('col-12');
            noProductsMessage.innerHTML = '<p class="text-center">Không tìm thấy sản phẩm nào.</p>';
            productList.appendChild(noProductsMessage);
            return;
        }

        // LẶP QUA DANH SÁCH SẢN PHẨM
        response.forEach(function (product) {
            const productCard = document.createElement('div');
            productCard.classList.add('col-md-6', 'col-lg-4');

            const card = document.createElement('div');
            card.classList.add('card', 'text-center', 'card-product');

            // VÙNG CHỨA ẢNH SẢN PHẨM
            const imgContainer = document.createElement('div');
            imgContainer.classList.add('card-product__img');

            const wrapper = document.createElement('div');
            wrapper.classList.add('product-image-wrapper');
            Object.assign(wrapper.style, {
                width: '250px',
                height: '250px',
                overflow: 'hidden',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                position: 'relative'
            });

            // ẢNH SẢN PHẨM
            const img = document.createElement('img');
            img.classList.add('product-image');
            img.src = `${contextPath}/admin/productImage?productId=${product.productId}`;
            img.alt = product.name;
            Object.assign(img.style, {
                width: '100%',
                height: '100%',
                objectFit: 'cover',
                display: 'block'
            });

            // HIỂN THỊ ICON KHI LỖI ẢNH
            const placeholder = document.createElement('div');
            placeholder.classList.add('product-placeholder');
            Object.assign(placeholder.style, {
                display: 'none',
                alignItems: 'center',
                justifyContent: 'center',
                backgroundColor: '#667eea',
                width: '100%',
                height: '100%'
            });

            const icon = document.createElement('i');
            icon.classList.add('fas', 'fa-image');
            icon.style.color = '#ccc';
            icon.style.fontSize = '32px';
            placeholder.appendChild(icon);

            // XỬ LÝ KHI ẢNH LỖI
            img.onerror = function () {
                img.style.display = 'none';
                placeholder.style.display = 'flex';
            };

            wrapper.appendChild(img);
            wrapper.appendChild(placeholder);
            imgContainer.appendChild(wrapper);

            // NÚT OVERLAY (HIỆN ĐÃ COMMENT)
            const overlay = document.createElement('ul');
            overlay.classList.add('card-product__imgOverlay');
            // CÓ THỂ MỞ COMMENT PHẦN NÀY ĐỂ THÊM NÚT
            /*
            ['ti-search', 'ti-shopping-cart', 'ti-heart'].forEach(iconClass => {
                const li = document.createElement('li');
                const btn = document.createElement('button');
                const i = document.createElement('i');
                i.classList.add(iconClass);
                btn.appendChild(i);
                li.appendChild(btn);
                overlay.appendChild(li);
            });
            */
            imgContainer.appendChild(overlay);
            card.appendChild(imgContainer);

            // THÔNG TIN SẢN PHẨM
            const body = document.createElement('div');
            body.classList.add('card-body');

            const desc = document.createElement('p');
            desc.textContent = product.description;

            const title = document.createElement('h4');
            title.classList.add('card-product__title');
            const link = document.createElement('a');
            link.href = `${contextPath}/product?action=details&id=${product.productId}`;
            link.textContent = product.name;
            title.appendChild(link);

            const price = document.createElement('p');
            price.classList.add('card-product__price');
            price.textContent = product.price !== undefined && product.price !== null ? `${product.price} ₫` : '$0';

            body.appendChild(title);
            body.appendChild(desc);
            body.appendChild(price);
            card.appendChild(body);
            productCard.appendChild(card);
            productList.appendChild(productCard);
        });
    } catch (error) {
        console.error("LỖI KHI ÁP DỤNG BỘ LỌC", error);
    } finally {
        $('#loading-spinner').hide(); // ẨN SPINNER
    }
}

// CHẠY KHI DOM ĐÃ SẴN SÀNG
$(function () {
    const slider = document.getElementById('price-range');
    if (slider) {
        // KHỞI TẠO THANH KÉO GIÁ
        noUiSlider.create(slider, {
            start: [parseInt($('#minPriceInput').val()), parseInt($('#maxPriceInput').val())],
            range: {
                min: parseInt($('#minPriceInput').attr('min')) || 0,
                max: parseInt($('#maxPriceInput').attr('max')) || 1000
            }
        });

        // CẬP NHẬT GIÁ KHI KÉO
        slider.noUiSlider.on('update', function (values) {
            $('#lower-value').text(Math.round(values[0]));
            $('#upper-value').text(Math.round(values[1]));
        });

        // ÁP DỤNG LỌC KHI THAY ĐỔI GIÁ
        slider.noUiSlider.on('change', function (values) {
            $('#minPriceInput').val(Math.round(values[0]));
            $('#maxPriceInput').val(Math.round(values[1]));
            applyFilters();
        });
    }

    // GÁN SỰ KIỆN CHO CÁC CHECKBOX
    $('#filterForm input[type="checkbox"]').on('change', applyFilters);

});
