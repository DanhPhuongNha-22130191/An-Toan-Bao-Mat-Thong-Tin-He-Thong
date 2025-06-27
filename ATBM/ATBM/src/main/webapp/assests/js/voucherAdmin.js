// ==========================
// Voucher Modal Handler
// ==========================

function openAddVoucherModal() {
    document.getElementById('modalTitle').textContent = 'Thêm voucher mới';
    document.getElementById('voucherForm').reset();
    document.getElementById('action').value = 'add';
    document.getElementById('voucherId').value = '';
    hideFormErrors();
    showVoucherModal();
}

function editVoucher(voucherId) {
    // Tìm <tr> chứa voucher đang chỉnh sửa (bổ sung data-id vào <tr> khi render JSP)
    const row = Array.from(document.querySelectorAll('#voucherTableBody tr')).find(tr =>
        tr.children[0].textContent.trim() == voucherId
    );
    if (!row) return;
    document.getElementById('modalTitle').textContent = 'Chỉnh sửa voucher';
    document.getElementById('action').value = 'edit';
    document.getElementById('voucherId').value = voucherId;
    document.getElementById('voucherCode').value = row.children[1].textContent.trim();
    document.getElementById('voucherName').value = row.children[2].textContent.trim();
    document.getElementById('percentDecrease').value = parseFloat(row.children[3].textContent);
    document.getElementById('quantity').value = parseInt(row.children[4].textContent, 10);

    // Lấy ngày hết hạn (bạn nên thêm data-value ISO vào <td> khi render JSP)
    const expirationTd = row.children[5];
    let isoVal = expirationTd.dataset.value;
    if (!isoVal) {
        // Nếu chưa có data-value, lấy lại theo pattern dd/MM/yyyy HH:mm, cố gắng chuyển sang yyyy-MM-ddTHH:mm
        const text = expirationTd.textContent.trim();
        const parts = text.split(/[\/ :]/);
        if (parts.length >= 5) {
            // dd/MM/yyyy HH:mm
            isoVal = `${parts[2]}-${parts[1].padStart(2,'0')}-${parts[0].padStart(2,'0')}T${parts[3].padStart(2,'0')}:${parts[4].padStart(2,'0')}`;
        } else {
            isoVal = "";
        }
    }
    document.getElementById('expirationTime').value = isoVal;
    hideFormErrors();
    showVoucherModal();
}

function deleteVoucher(voucherId) {
    if (confirm('Bạn có chắc chắn muốn xóa voucher này?')) {
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = 'voucher';
        form.innerHTML = `
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="voucherId" value="${voucherId}">
        `;
        document.body.appendChild(form);
        form.submit();
    }
}

function showVoucherModal() {
    document.getElementById('voucherModal').classList.add('show');
}
function closeVoucherModal() {
    document.getElementById('voucherModal').classList.remove('show');
}
document.getElementById('voucherModal').addEventListener('click', function(e) {
    if (e.target === this) closeVoucherModal();
});
document.querySelectorAll('.modal-close').forEach(btn => {
    btn.addEventListener('click', closeVoucherModal);
});

// ==========================
// Form Validation
// ==========================
function hideFormErrors() {
    document.querySelectorAll('.form-error').forEach(e => e.style.display = 'none');
}
function showFormError(id, msg) {
    const el = document.getElementById(id);
    if (el) {
        el.textContent = msg;
        el.style.display = '';
    }
}
function validateVoucherForm() {
    hideFormErrors();
    let valid = true;
    const code = document.getElementById('voucherCode').value.trim();
    const name = document.getElementById('voucherName').value.trim();
    const percent = parseFloat(document.getElementById('percentDecrease').value);
    const quantity = parseInt(document.getElementById('quantity').value, 10);
    const expiration = document.getElementById('expirationTime').value;

    if (!code) {
        showFormError('voucherCodeError', 'Mã voucher không được để trống');
        valid = false;
    }
    if (!name) {
        showFormError('voucherNameError', 'Tên voucher không được để trống');
        valid = false;
    }
    if (isNaN(percent) || percent < 0 || percent > 100) {
        showFormError('percentDecreaseError', 'Phần trăm giảm giá phải từ 0-100');
        valid = false;
    }
    if (isNaN(quantity) || quantity < 1) {
        showFormError('quantityError', 'Số lượng phải lớn hơn 0');
        valid = false;
    }
    if (!expiration) {
        showFormError('expirationTimeError', 'Vui lòng chọn ngày hết hạn');
        valid = false;
    }
    return valid;
}

// Gửi form, kiểm tra dữ liệu trước khi submit
document.getElementById('voucherForm').addEventListener('submit', function(e) {
    if (!validateVoucherForm()) {
        e.preventDefault();
        return false;
    }
    // Để submit tự nhiên về server xử lý
});

// ==========================
// Filter Table Voucher (trực tiếp trên DOM)
// ==========================
document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('searchInput');
    const filterStatus = document.getElementById('filterStatus');
    const filterType = document.getElementById('filterType');
    const tableBody = document.getElementById('voucherTableBody');

    function normalize(str) {
        return (str || '').toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "");
    }

    function getStatus(row) {
        // Phân tích trạng thái từ text
        const statusTd = row.children[6];
        if (!statusTd) return '';
        const text = normalize(statusTd.textContent);
        if (text.includes('hoạt động')) return 'valid';
        if (text.includes('hết hạn')) return 'expired';
        if (text.includes('hết số lượng')) return 'out_of_stock';
        return '';
    }
    function getType(row) {
        // Phân loại loại voucher: nếu giảm giá > 0 thì percentage, ==0 thì fixed
        const percent = parseFloat(row.children[3].textContent);
        if (percent > 0 && percent <= 100) return 'percentage';
        if (percent === 0) return 'fixed';
        return '';
    }

    function filterTable() {
        const search = normalize(searchInput?.value.trim() || '');
        const status = filterStatus?.value || '';
        const type = filterType?.value || '';

        let found = false;

        Array.from(tableBody.querySelectorAll('tr')).forEach(row => {
            // Nếu là dòng thông báo, bỏ qua
            if (!row.children || row.children.length < 7) return;

            const code = normalize(row.children[1].textContent);
            const name = normalize(row.children[2].textContent);
            const rowStatus = getStatus(row);
            const rowType = getType(row);

            // Kiểm tra search (theo mã hoặc tên)
            let matched = !search || code.includes(search) || name.includes(search);

            // Kiểm tra filter trạng thái
            if (matched && status) {
                matched = (status === rowStatus);
            }
            // Kiểm tra filter loại
            if (matched && type) {
                matched = (type === rowType);
            }

            row.style.display = matched ? "" : "none";
            if (matched) found = true;
        });

        // Hiện thông báo nếu không tìm thấy voucher nào
        let noVoucherRow = document.getElementById('noVoucherRow');
        if (!found) {
            if (!noVoucherRow) {
                const tr = document.createElement('tr');
                tr.id = 'noVoucherRow';
                tr.innerHTML = `<td colspan="8" class="text-center">Không tìm thấy voucher nào</td>`;
                tableBody.appendChild(tr);
            }
        } else {
            if (noVoucherRow) noVoucherRow.remove();
        }
    }

    if (searchInput) searchInput.addEventListener('input', filterTable);
    if (filterStatus) filterStatus.addEventListener('change', filterTable);
    if (filterType) filterType.addEventListener('change', filterTable);

    // Gọi filterTable khi load lần đầu (nếu có giá trị filter)
    filterTable();
});

// ==========================
// Đăng ký nút Thêm voucher
// ==========================
document.querySelectorAll('.btn.btn-primary').forEach(btn => {
    if (btn.textContent.includes('Thêm voucher')) {
        btn.addEventListener('click', openAddVoucherModal);
    }
});

// ==========================
// Đăng ký nút Sửa/Xóa (nếu render động, có thể dùng delegation)
// ==========================
window.editVoucher = editVoucher;
window.deleteVoucher = deleteVoucher;
window.openAddVoucherModal = openAddVoucherModal;
window.closeVoucherModal = closeVoucherModal;