/**
 * TẠO VÀ CẤU HÌNH MODAL CHO FORM THÊM/CHỈNH SỬA SẢN PHẨM
 * @param {string} containerId - ID CỦA CONTAINER CHỨA FORM
 * @param {string} formType - LOẠI FORM ('add' HOẶC 'edit')
 * @returns {Object} ĐỐI TƯỢNG CHỨA modalOverlay, form VÀ closeButton
 */
function createModal(containerId, formType) {
    const container = document.getElementById(containerId);
    const form = container.querySelector('form');

    // TẠO LỚP NỀN MỜ PHỦ TOÀN MÀN HÌNH
    const modalOverlay = document.createElement('div');
    modalOverlay.className = 'modal-overlay';

    // TẠO NỘI DUNG CỦA MODAL
    const modalContent = document.createElement('div');
    modalContent.className = 'modal-content';

    // TẠO NÚT ĐÓNG MODAL
    const closeButton = document.createElement('button');
    closeButton.className = 'close-modal';
    closeButton.innerHTML = '×';
    closeButton.style.position = 'absolute';
    closeButton.style.top = '10px';
    closeButton.style.right = '15px';

    // ĐƯA FORM VÀO NỘI DUNG MODAL
    modalContent.appendChild(closeButton);
    modalContent.appendChild(form);
    modalOverlay.appendChild(modalContent);
    document.body.appendChild(modalOverlay);

    return { modalOverlay, form, closeButton };
}

/**
 * THIẾT LẬP SỰ KIỆN ĐÓNG MODAL
 * @param {HTMLElement} modalOverlay - LỚP NỀN MODAL
 * @param {HTMLElement} closeButton - NÚT ĐÓNG MODAL
 * @param {string} cancelButtonId - ID CỦA NÚT "HỦY" (NẾU CÓ)
 */
function setupCloseEvents(modalOverlay, closeButton, cancelButtonId) {
    // ĐÓNG MODAL KHI NHẤN NÚT ĐÓNG
    closeButton.addEventListener('click', () => {
        modalOverlay.classList.remove('active');
    });

    // ĐÓNG MODAL KHI NHẤN VÀO VÙNG NỀN NGOÀI MODAL
    modalOverlay.addEventListener('click', (e) => {
        if (e.target === modalOverlay) {
            modalOverlay.classList.remove('active');
        }
    });

    // ĐÓNG MODAL KHI NHẤN NÚT HỦY (NẾU TỒN TẠI)
    if (cancelButtonId) {
        document.getElementById(cancelButtonId).addEventListener('click', () => {
            modalOverlay.classList.remove('active');
        });
    }
}

/**
 * HIỂN THỊ ẢNH XEM TRƯỚC KHI CHỌN FILE ẢNH
 * @param {HTMLInputElement} inputElement - INPUT CHỌN FILE
 * @param {string} previewId - ID CỦA PHẦN TỬ IMG HIỂN THỊ PREVIEW
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
 * ĐỔ DỮ LIỆU SẢN PHẨM VÀO FORM CHỈNH SỬA
 * @param {HTMLFormElement} form - FORM CHỈNH SỬA
 * @param {Object} dataset - DỮ LIỆU SẢN PHẨM TỪ data-* ATTRIBUTE
 */
function populateEditForm(form, dataset) {
    form.querySelector('input[name="productId"]').value = dataset.productId;
    form.querySelector('#name').value = dataset.name;
    form.querySelector('#price').value = dataset.price;
    form.querySelector('#description').value = dataset.description;
    form.querySelector('#stock').value = dataset.stock;
    form.querySelector('#brandId').value = dataset.brandId;
    form.querySelector('#status').value = dataset.status;

    // HIỂN THỊ ẢNH HIỆN TẠI (NẾU CÓ)
    const preview = form.querySelector('#currentImagePreview');
    if (dataset.image) {
        preview.src = dataset.image;
        preview.style.display = 'block';
    } else {
        preview.style.display = 'none';
    }

    // RESET ẢNH PREVIEW MỚI
    const newPreview = document.getElementById('newImagePreview');
    newPreview.src = '';
    newPreview.style.display = 'none';
}

/**
 * HÀM XÁC NHẬN VÀ THỰC HIỆN XOÁ SẢN PHẨM
 * @param {string} productId - ID SẢN PHẨM CẦN XOÁ
 */
function confirmDelete(productId) {
    if (confirm("Bạn có chắc chắn muốn xóa sản phẩm này không?")) {
        window.location.href = `/ATBM/admin/deleteProduct?id=${productId}`;
    }
}

// KHI DOM ĐÃ SẴN SÀNG
document.addEventListener('DOMContentLoaded', () => {
    // KHỞI TẠO MODAL THÊM SẢN PHẨM
    const addModal = createModal('addFormContainer', 'add');
    setupCloseEvents(addModal.modalOverlay, addModal.closeButton, 'cancelAddForm');

    // MỞ MODAL THÊM KHI CLICK NÚT "THÊM"
    document.querySelector('button.btn-primary').addEventListener('click', () => {
        addModal.modalOverlay.classList.add('active');
    });

    // THIẾT LẬP XEM TRƯỚC ẢNH KHI THÊM
    setupImagePreview(
        document.getElementById('add-image'),
        'addImagePreview'
    );

    // KHỞI TẠO MODAL CHỈNH SỬA SẢN PHẨM
    const editModal = createModal('editFormContainer', 'edit');
    setupCloseEvents(editModal.modalOverlay, editModal.closeButton);

    // GẮN SỰ KIỆN CHO NÚT "CHỈNH SỬA"
    document.querySelectorAll('button.btn-warning').forEach(button => {
        button.addEventListener('click', () => {
            populateEditForm(editModal.form, button.dataset);
            editModal.modalOverlay.classList.add('active');
        });
    });

    // THIẾT LẬP XEM TRƯỚC ẢNH KHI CHỈNH SỬA
    setupImagePreview(
        editModal.form.querySelector('#image'),
        'newImagePreview'
    );
});

// CONTEXT PATH LẤY TỪ SERVER (JSP)
const contextPath = '<%= request.getContextPath() %>';
