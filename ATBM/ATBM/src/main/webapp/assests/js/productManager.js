/**
 * Tạo modal dùng cho thêm hoặc chỉnh sửa sản phẩm
 */
function createModal(containerId) {
    const container = document.getElementById(containerId);
    const form = container.querySelector('form');

    const modalOverlay = document.createElement('div');
    modalOverlay.className = 'modal-overlay';

    const modalContent = document.createElement('div');
    modalContent.className = 'modal-content';

    const closeButton = document.createElement('button');
    closeButton.className = 'close-modal';
    closeButton.innerHTML = '×';
    closeButton.style.position = 'absolute';
    closeButton.style.top = '10px';
    closeButton.style.right = '15px';

    modalContent.appendChild(closeButton);
    modalContent.appendChild(form);
    modalOverlay.appendChild(modalContent);
    document.body.appendChild(modalOverlay);

    return { modalOverlay, form, closeButton };
}

/**
 * Đóng modal khi click ngoài hoặc nút huỷ
 */
function setupCloseEvents(modalOverlay, closeButton, cancelButtonId) {
    closeButton.addEventListener('click', () => {
        modalOverlay.classList.remove('active');
    });

    modalOverlay.addEventListener('click', (e) => {
        if (e.target === modalOverlay) {
            modalOverlay.classList.remove('active');
        }
    });

    if (cancelButtonId) {
        const cancelBtn = document.getElementById(cancelButtonId);
        if (cancelBtn) {
            cancelBtn.addEventListener('click', () => {
                modalOverlay.classList.remove('active');
            });
        }
    }
}

/**
 * Xem trước ảnh
 */
function setupImagePreview(inputElement, previewId) {
    inputElement.addEventListener('change', function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                const preview = document.getElementById(previewId);
                preview.src = e.target.result;
                preview.style.display = 'block';
            };
            reader.readAsDataURL(file);
        }
    });
}

/**
 * Đổ dữ liệu sản phẩm vào form sửa
 */
function populateEditForm(form, dataset) {
    form.querySelector('input[name="productId"]').value = dataset.productId || '';
    form.querySelector('#name').value = dataset.name || '';
    form.querySelector('#price').value = dataset.price || '';
    form.querySelector('#description').value = dataset.description || '';
    form.querySelector('#stock').value = dataset.stock || '';
    form.querySelector('#brandId').value = dataset.brandId || '';

    const preview = form.querySelector('#currentImagePreview');
    if (dataset.image) {
        preview.src = dataset.image;
        preview.style.display = 'block';
    } else {
        preview.style.display = 'none';
    }

    const newPreview = document.getElementById('newImagePreview');
    if (newPreview) {
        newPreview.src = '';
        newPreview.style.display = 'none';
    }
}

/**
 * Gửi POST request để cập nhật sản phẩm qua /admin/product/update
 */
function submitEditForm(form) {
    const formData = new FormData();

    formData.append('productId', form.querySelector('input[name="productId"]').value);
    formData.append('name', form.querySelector('#name').value);
    formData.append('price', form.querySelector('#price').value);
    formData.append('description', form.querySelector('#description').value);
    formData.append('stock', form.querySelector('#stock').value);
    formData.append('brandId', form.querySelector('#brandId').value);

    const imageInput = form.querySelector('#image');
    if (imageInput && imageInput.files.length > 0) {
        formData.append('image', imageInput.files[0]);
    }

    fetch('/ATBM/admin/product/update', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                window.location.reload();
            } else {
                alert('Cập nhật sản phẩm thất bại.');
            }
        })
        .catch(() => alert('Lỗi khi gửi dữ liệu.'));
}

/**
 * Gửi DELETE request để xóa sản phẩm
 */
function confirmDelete(productId) {
    if (confirm("Bạn có chắc chắn muốn xóa sản phẩm này không?")) {
        fetch(`/ATBM/admin/product/${productId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    alert("Xoá thành công!");
                    location.reload();
                } else {
                    alert("Xoá sản phẩm thất bại!");
                }
            })
            .catch(error => {
                console.error("Lỗi khi xoá sản phẩm:", error);
                alert("Đã xảy ra lỗi!");
            });
    }
}

// DOM ready
document.addEventListener('DOMContentLoaded', () => {
    // Modal thêm
    const addModal = createModal('addFormContainer');
    setupCloseEvents(addModal.modalOverlay, addModal.closeButton, 'cancelAddForm');

    document.querySelector('button.btn-primary').addEventListener('click', () => {
        addModal.modalOverlay.classList.add('active');
    });

    setupImagePreview(
        document.getElementById('add-image'),
        'addImagePreview'
    );

    // Modal sửa
    const editModal = createModal('editFormContainer');
    setupCloseEvents(editModal.modalOverlay, editModal.closeButton);

    document.querySelectorAll('button.btn-warning').forEach(button => {
        button.addEventListener('click', () => {
            populateEditForm(editModal.form, button.dataset);
            editModal.modalOverlay.classList.add('active');

            editModal.form.onsubmit = function (e) {
                e.preventDefault();
                submitEditForm(editModal.form);
            };
        });
    });

    setupImagePreview(
        editModal.form.querySelector('#image'),
        'newImagePreview'
    );
});
