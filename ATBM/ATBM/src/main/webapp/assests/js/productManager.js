/**
 * Tạo và cấu hình modal cho form thêm/sửa sản phẩm
 * @param {string} containerId - ID của container chứa form
 * @param {string} formType - Loại form ('add' hoặc 'edit')
 * @returns {Object} Đối tượng chứa modalOverlay, form và closeButton
 */
function createModal(containerId, formType) {
    const container = document.getElementById(containerId);
    const form = container.querySelector('form');

    // Tạo overlay nền mờ
    const modalOverlay = document.createElement('div');
    modalOverlay.className = 'modal-overlay';

    // Tạo nội dung modal
    const modalContent = document.createElement('div');
    modalContent.className = 'modal-content';

    // Tạo nút đóng modal
    const closeButton = document.createElement('button');
    closeButton.className = 'close-modal';
    closeButton.innerHTML = '×';
    closeButton.style.position = 'absolute';
    closeButton.style.top = '10px';
    closeButton.style.right = '15px';

    // Gắn form vào modal content
    modalContent.appendChild(closeButton);
    modalContent.appendChild(form);
    modalOverlay.appendChild(modalContent);
    document.body.appendChild(modalOverlay);

    return { modalOverlay, form, closeButton };
}

/**
 * Thiết lập sự kiện đóng modal
 * @param {HTMLElement} modalOverlay - Phần tử overlay của modal
 * @param {HTMLElement} closeButton - Nút đóng modal
 * @param {string} cancelButtonId - ID của nút hủy (nếu có)
 */
function setupCloseEvents(modalOverlay, closeButton, cancelButtonId) {
    // Đóng modal khi nhấn nút đóng
    closeButton.addEventListener('click', () => {
        modalOverlay.classList.remove('active');
    });

    // Đóng modal khi nhấn bên ngoài
    modalOverlay.addEventListener('click', (e) => {
        if (e.target === modalOverlay) {
            modalOverlay.classList.remove('active');
        }
    });

    // Đóng modal khi nhấn nút hủy (nếu có)
    if (cancelButtonId) {
        document.getElementById(cancelButtonId).addEventListener('click', () => {
            modalOverlay.classList.remove('active');
        });
    }
}

/**
 * Thiết lập preview ảnh khi chọn file
 * @param {HTMLInputElement} inputElement - Input file
 * @param {string} previewId - ID của phần tử preview ảnh
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
 * Gán dữ liệu vào form chỉnh sửa
 * @param {HTMLFormElement} form - Form chỉnh sửa
 * @param {Object} dataset - Dữ liệu sản phẩm
 */
function populateEditForm(form, dataset) {
    form.querySelector('input[name="productId"]').value = dataset.productId;
    form.querySelector('#name').value = dataset.name;
    form.querySelector('#price').value = dataset.price;
    form.querySelector('#description').value = dataset.description;
    form.querySelector('#stock').value = dataset.stock;
    form.querySelector('#brandId').value = dataset.brandId;
    form.querySelector('#status').value = dataset.status;

    // Xử lý ảnh hiện tại
    const preview = form.querySelector('#currentImagePreview');
    if (dataset.image) {
        preview.src = dataset.image;
        preview.style.display = 'block';
    } else {
        preview.style.display = 'none';
    }

    // Reset preview ảnh mới
    const newPreview = document.getElementById('newImagePreview');
    newPreview.src = '';
    newPreview.style.display = 'none';
}

/**
 * Xác nhận xóa sản phẩm
 * @param {string} productId - ID của sản phẩm cần xóa
 */
function confirmDelete(productId) {
    if (confirm("Bạn có chắc chắn muốn xóa sản phẩm này không?")) {
        window.location.href = `/ATBM/admin/deleteProduct?id=${productId}`;
    }
}

// Khởi tạo khi DOM được tải
document.addEventListener('DOMContentLoaded', () => {
    // Khởi tạo modal thêm sản phẩm
    const addModal = createModal('addFormContainer', 'add');
    setupCloseEvents(addModal.modalOverlay, addModal.closeButton, 'cancelAddForm');

    // Gắn sự kiện mở modal thêm sản phẩm
    document.querySelector('button.btn-primary').addEventListener('click', () => {
        addModal.modalOverlay.classList.add('active');
    });

    // Thiết lập preview ảnh thêm sản phẩm
    setupImagePreview(
        document.getElementById('add-image'),
        'addImagePreview'
    );

    // Khởi tạo modal chỉnh sửa sản phẩm
    const editModal = createModal('editFormContainer', 'edit');
    setupCloseEvents(editModal.modalOverlay, editModal.closeButton);

    // Thiết lập sự kiện cho các nút chỉnh sửa
    document.querySelectorAll('button.btn-warning').forEach(button => {
        button.addEventListener('click', () => {
            populateEditForm(editModal.form, button.dataset);
            editModal.modalOverlay.classList.add('active');
        });
    });

    // Thiết lập preview ảnh chỉnh sửa
    setupImagePreview(
        editModal.form.querySelector('#image'),
        'newImagePreview'
    );
});

// Context path từ server
const contextPath = '<%= request.getContextPath() %>';