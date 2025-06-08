document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM Loaded!');
});

const contextPath = '<%= request.getContextPath() %>';

document.addEventListener('DOMContentLoaded', () => {
    const editFormContainer = document.getElementById('editFormContainer');

    // Tạo overlay nền mờ
    const modalOverlay = document.createElement('div');
    modalOverlay.className = 'modal-overlay';

    // Tạo nội dung modal
    const modalContent = document.createElement('div');
    modalContent.className = 'modal-content';

    // Nút đóng modal
    const closeButton = document.createElement('button');
    closeButton.className = 'close-modal';
    closeButton.innerHTML = '&times;';

    // Di chuyển form vào nội dung modal
    const form = editFormContainer.querySelector('form');
    modalContent.appendChild(closeButton);
    modalContent.appendChild(form);
    modalOverlay.appendChild(modalContent);
    document.body.appendChild(modalOverlay);

    // Hiển thị modal khi nhấn nút "Sửa"
    document.querySelectorAll('button.btn-warning').forEach(button => {
        button.addEventListener('click', () => {
            const dataset = button.dataset;

            // Gán dữ liệu vào form
            form.querySelector('input[name="productId"]').value = dataset.productId;
            form.querySelector('#name').value = dataset.name;
            form.querySelector('#price').value = dataset.price;
            form.querySelector('#description').value = dataset.description;
            form.querySelector('#stock').value = dataset.stock;
            form.querySelector('#brandId').value = dataset.brandId;
            form.querySelector('#status').value = dataset.status;

            // Hiển thị ảnh hiện tại
            const preview = form.querySelector('#currentImagePreview');
            if (dataset.image) {
                preview.src = contextPath + '/assets/img/product/' + dataset.image;
                preview.style.display = 'block';
            } else {
                preview.style.display = 'none';
            }

            // Reset preview ảnh mới
            const newPreview = document.getElementById('newImagePreview');
            newPreview.src = '';
            newPreview.style.display = 'none';

            // Hiển thị modal
            modalOverlay.classList.add('active');
        });
    });

    // Đóng modal khi nhấn nút đóng
    closeButton.addEventListener('click', () => {
        modalOverlay.classList.remove('active');
    });

    // Đóng modal khi nhấn ngoài nội dung
    modalOverlay.addEventListener('click', (e) => {
        if (e.target === modalOverlay) {
            modalOverlay.classList.remove('active');
        }
    });

    // Xem trước ảnh mới chọn
    form.querySelector('#image').addEventListener('change', function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                const preview = document.getElementById('newImagePreview');
                preview.src = e.target.result;
                preview.style.display = 'block';
            };
            reader.readAsDataURL(file);
        }
    });
});
