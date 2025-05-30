// Voucher Management System
class VoucherManager {
    constructor() {
        this.vouchers = [];
        this.currentEditId = null;
        this.init();
    }

    init() {
        this.loadVouchers();
        this.bindEvents();
        this.updateStats();
        this.renderTable();
    }

    // Load vouchers from localStorage or initialize with sample data
    loadVouchers() {
        const savedVouchers = localStorage.getItem('vouchers');
        if (savedVouchers) {
            this.vouchers = JSON.parse(savedVouchers);
        } else {
            // Sample data
            this.vouchers = [
                {
                    id: 1,
                    code: 'WELCOME10',
                    name: 'Chào mừng khách hàng mới',
                    type: 'percentage',
                    value: 10,
                    minOrderValue: 100000,
                    maxDiscount: 50000,
                    quantity: 100,
                    used: 25,
                    expiryDate: '2024-12-31T23:59',
                    description: 'Voucher giảm giá cho khách hàng mới',
                    status: 'active',
                    createdAt: new Date().toISOString()
                },
                {
                    id: 2,
                    code: 'SUMMER50K',
                    name: 'Khuyến mãi mùa hè',
                    type: 'fixed',
                    value: 50000,
                    minOrderValue: 200000,
                    maxDiscount: null,
                    quantity: 50,
                    used: 12,
                    expiryDate: '2024-08-31T23:59',
                    description: 'Giảm 50k cho đơn hàng từ 200k',
                    status: 'active',
                    createdAt: new Date().toISOString()
                },
                {
                    id: 3,
                    code: 'EXPIRED20',
                    name: 'Voucher hết hạn',
                    type: 'percentage',
                    value: 20,
                    minOrderValue: 150000,
                    maxDiscount: 100000,
                    quantity: 30,
                    used: 30,
                    expiryDate: '2024-01-31T23:59',
                    description: 'Voucher đã hết hạn',
                    status: 'expired',
                    createdAt: new Date().toISOString()
                }
            ];
            this.saveVouchers();
        }
    }

    // Save vouchers to localStorage
    saveVouchers() {
        localStorage.setItem('vouchers', JSON.stringify(this.vouchers));
    }

    // Bind event listeners
    bindEvents() {
        // Search functionality
        document.getElementById('searchInput').addEventListener('input', (e) => {
            this.filterTable();
        });

        // Filter by status
        document.getElementById('filterStatus').addEventListener('change', () => {
            this.filterTable();
        });

        // Filter by type
        document.getElementById('filterType').addEventListener('change', () => {
            this.filterTable();
        });

        // Form submission
        document.getElementById('voucherForm').addEventListener('submit', (e) => {
            e.preventDefault();
            this.saveVoucher();
        });

        // Discount type change
        document.getElementById('discountType').addEventListener('change', (e) => {
            this.handleDiscountTypeChange(e.target.value);
        });

        // Close modal on overlay click
        document.getElementById('voucherModal').addEventListener('click', (e) => {
            if (e.target.classList.contains('modal-overlay')) {
                this.closeVoucherModal();
            }
        });

        // Keyboard shortcuts
        document.addEventListener('keydown', (e) => {
            if (e.key === 'Escape') {
                this.closeVoucherModal();
            }
        });
    }

    // Update statistics
    updateStats() {
        const activeVouchers = this.vouchers.filter(v => v.status === 'active').length;
        const expiredVouchers = this.vouchers.filter(v => v.status === 'expired').length;
        const totalUsed = this.vouchers.reduce((sum, v) => sum + v.used, 0);

        document.getElementById('totalVouchers').textContent = this.vouchers.length;
        document.getElementById('activeVouchers').textContent = activeVouchers;
        document.getElementById('expiredVouchers').textContent = expiredVouchers;
        document.getElementById('usedVouchers').textContent = totalUsed;

        // Animate numbers
        this.animateNumbers();
    }

    // Animate number counters
    animateNumbers() {
        const numbers = document.querySelectorAll('.stat-card h3');
        numbers.forEach(number => {
            const target = parseInt(number.textContent);
            let current = 0;
            const increment = target / 20;
            const timer = setInterval(() => {
                current += increment;
                if (current >= target) {
                    current = target;
                    clearInterval(timer);
                }
                number.textContent = Math.floor(current);
            }, 50);
        });
    }

    // Render voucher table
    renderTable() {
        const tbody = document.getElementById('voucherTableBody');
        tbody.innerHTML = '';

        let filteredVouchers = this.getFilteredVouchers();
        
        if (filteredVouchers.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="10" style="text-align: center; padding: 2rem; color: #7f8c8d;">
                        <i class="fas fa-inbox" style="font-size: 3rem; margin-bottom: 1rem; display: block;"></i>
                        Không tìm thấy voucher nào
                    </td>
                </tr>
            `;
            return;
        }

        filteredVouchers.forEach(voucher => {
            const row = this.createTableRow(voucher);
            tbody.appendChild(row);
        });

        // Add row animations
        setTimeout(() => {
            const rows = tbody.querySelectorAll('tr');
            rows.forEach((row, index) => {
                row.style.animation = `fadeUp 0.5s ease ${index * 0.1}s both`;
            });
        }, 100);
    }

    // Create table row element
    createTableRow(voucher) {
        const row = document.createElement('tr');
        
        const statusClass = `status-${voucher.status}`;
        const typeClass = voucher.type === 'percentage' ? 'voucher-percentage' : 'voucher-fixed';
        const typeText = voucher.type === 'percentage' ? 'Giảm %' : 'Giảm cố định';
        const valueText = voucher.type === 'percentage' 
            ? `${voucher.value}%` 
            : this.formatCurrency(voucher.value);
        
        const maxDiscountText = voucher.maxDiscount 
            ? this.formatCurrency(voucher.maxDiscount)
            : 'Không giới hạn';

        row.innerHTML = `
            <td><strong>${voucher.code}</strong></td>
            <td>${voucher.name}</td>
            <td><span class="voucher-type ${typeClass}">${typeText}</span></td>
            <td><strong>${valueText}</strong></td>
            <td>${this.formatCurrency(voucher.minOrderValue)}</td>
            <td>${voucher.quantity}</td>
            <td>${voucher.used}</td>
            <td>${this.formatDate(voucher.expiryDate)}</td>
            <td><span class="status-badge ${statusClass}">${this.getStatusText(voucher.status)}</span></td>
            <td>
                <div class="action-buttons">
                    <button class="btn btn-warning btn-sm" onclick="voucherManager.editVoucher(${voucher.id})" title="Chỉnh sửa">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-danger btn-sm" onclick="voucherManager.deleteVoucher(${voucher.id})" title="Xóa">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            </td>
        `;

        return row;
    }

    // Get filtered vouchers based on search and filters
    getFilteredVouchers() {
        const searchTerm = document.getElementById('searchInput').value.toLowerCase();
        const statusFilter = document.getElementById('filterStatus').value;
        const typeFilter = document.getElementById('filterType').value;

        return this.vouchers.filter(voucher => {
            const matchesSearch = !searchTerm || 
                voucher.code.toLowerCase().includes(searchTerm) ||
                voucher.name.toLowerCase().includes(searchTerm);
            
            const matchesStatus = !statusFilter || voucher.status === statusFilter;
            const matchesType = !typeFilter || voucher.type === typeFilter;

            return matchesSearch && matchesStatus && matchesType;
        });
    }

    // Filter table based on search and filters
    filterTable() {
        this.renderTable();
    }

    // Open add voucher modal
    openAddVoucherModal() {
        this.currentEditId = null;
        document.getElementById('modalTitle').textContent = 'Thêm voucher mới';
        document.getElementById('voucherForm').reset();
        this.clearFormErrors();
        this.showModal();
    }

    // Edit voucher
    editVoucher(id) {
        const voucher = this.vouchers.find(v => v.id === id);
        if (!voucher) return;

        this.currentEditId = id;
        document.getElementById('modalTitle').textContent = 'Chỉnh sửa voucher';
        
        // Fill form with voucher data
        document.getElementById('voucherCode').value = voucher.code;
        document.getElementById('voucherName').value = voucher.name;
        document.getElementById('discountType').value = voucher.type;
        document.getElementById('discountValue').value = voucher.value;
        document.getElementById('minOrderValue').value = voucher.minOrderValue;
        document.getElementById('maxDiscount').value = voucher.maxDiscount || '';
        document.getElementById('quantity').value = voucher.quantity;
        document.getElementById('expiryDate').value = voucher.expiryDate;
        document.getElementById('description').value = voucher.description || '';

        this.handleDiscountTypeChange(voucher.type);
        this.clearFormErrors();
        this.showModal();
    }

    // Delete voucher
    deleteVoucher(id) {
        const voucher = this.vouchers.find(v => v.id === id);
        if (!voucher) return;

        if (confirm(`Bạn có chắc chắn muốn xóa voucher "${voucher.name}"?`)) {
            this.vouchers = this.vouchers.filter(v => v.id !== id);
            this.saveVouchers();
            this.updateStats();
            this.renderTable();
            this.showNotification('Xóa voucher thành công!', 'success');
        }
    }

    // Save voucher (add or edit)
    saveVoucher() {
        if (!this.validateForm()) {
            return;
        }

        const formData = this.getFormData();
        
        if (this.currentEditId) {
            // Update existing voucher
            const index = this.vouchers.findIndex(v => v.id === this.currentEditId);
            if (index !== -1) {
                this.vouchers[index] = { ...this.vouchers[index], ...formData };
                this.showNotification('Cập nhật voucher thành công!', 'success');
            }
        } else {
            // Add new voucher
            const newVoucher = {
                id: Date.now(),
                ...formData,
                used: 0,
                createdAt: new Date().toISOString()
            };
            this.vouchers.push(newVoucher);
            this.showNotification('Thêm voucher thành công!', 'success');
        }

        this.saveVouchers();
        this.updateStats();
        this.renderTable();
        this.closeVoucherModal();
    }

    // Get form data
    getFormData() {
        const expiryDate = document.getElementById('expiryDate').value;
        const status = new Date(expiryDate) > new Date() ? 'active' : 'expired';

        return {
            code: document.getElementById('voucherCode').value.trim(),
            name: document.getElementById('voucherName').value.trim(),
            type: document.getElementById('discountType').value,
            value: parseFloat(document.getElementById('discountValue').value),
            minOrderValue: parseFloat(document.getElementById('minOrderValue').value),
            maxDiscount: document.getElementById('maxDiscount').value ? 
                parseFloat(document.getElementById('maxDiscount').value) : null,
            quantity: parseInt(document.getElementById('quantity').value),
            expiryDate: expiryDate,
            description: document.getElementById('description').value.trim(),
            status: status
        };
    }

    // Validate form
    validateForm() {
        let isValid = true;
        this.clearFormErrors();

        // Validate voucher code
        const code = document.getElementById('voucherCode').value.trim();
        if (!code) {
            this.showFieldError('voucherCode', 'Mã voucher không được để trống');
            isValid = false;
        } else if (this.isCodeExists(code)) {
            this.showFieldError('voucherCode', 'Mã voucher đã tồn tại');
            isValid = false;
        }

        // Validate voucher name
        const name = document.getElementById('voucherName').value.trim();
        if (!name) {
            this.showFieldError('voucherName', 'Tên voucher không được để trống');
            isValid = false;
        }

        // Validate discount type
        const discountType = document.getElementById('discountType').value;
        if (!discountType) {
            this.showFieldError('discountType', 'Vui lòng chọn loại giảm giá');
            isValid = false;
        }

        // Validate discount value
        const discountValue = parseFloat(document.getElementById('discountValue').value);
        if (!discountValue || discountValue <= 0) {
            this.showFieldError('discountValue', 'Giá trị giảm phải lớn hơn 0');
            isValid = false;
        } else if (discountType === 'percentage' && discountValue > 100) {
            this.showFieldError('discountValue', 'Giá trị giảm theo % không được vượt quá 100');
            isValid = false;
        }

        // Validate minimum order value
        const minOrderValue = parseFloat(document.getElementById('minOrderValue').value);
        if (!minOrderValue || minOrderValue < 0) {
            this.showFieldError('minOrderValue', 'Giá trị đơn hàng tối thiểu không hợp lệ');
            isValid = false;
        }

        // Validate max discount for percentage type
        const maxDiscount = document.getElementById('maxDiscount').value;
        if (discountType === 'percentage' && maxDiscount && parseFloat(maxDiscount) <= 0) {
            this.showFieldError('maxDiscount', 'Giá trị giảm tối đa không hợp lệ');
            isValid = false;
        }

        // Validate quantity
        const quantity = parseInt(document.getElementById('quantity').value);
        if (!quantity || quantity <= 0) {
            this.showFieldError('quantity', 'Số lượng phải lớn hơn 0');
            isValid = false;
        }

        // Validate expiry date
        const expiryDate = document.getElementById('expiryDate').value;
        if (!expiryDate) {
            this.showFieldError('expiryDate', 'Vui lòng chọn ngày hết hạn');
            isValid = false;
        } else if (new Date(expiryDate) <= new Date()) {
            this.showFieldError('expiryDate', 'Ngày hết hạn phải sau thời điểm hiện tại');
            isValid = false;
        }

        return isValid;
    }

    // Check if voucher code exists
    isCodeExists(code) {
        return this.vouchers.some(v => 
            v.code.toLowerCase() === code.toLowerCase() && 
            v.id !== this.currentEditId
        );
    }

    // Show field error
    showFieldError(fieldId, message) {
        const field = document.getElementById(fieldId);
        const errorElement = document.getElementById(fieldId + 'Error');
        
        field.classList.add('error');
        errorElement.textContent = message;
        errorElement.classList.add('show');
    }

    // Clear form errors
    clearFormErrors() {
        const errorElements = document.querySelectorAll('.form-error');
        const inputElements = document.querySelectorAll('.form-input');
        
        errorElements.forEach(el => {
            el.classList.remove('show');
        });
        
        inputElements.forEach(el => {
            el.classList.remove('error');
        });
    }

    // Handle discount type change
    handleDiscountTypeChange(type) {
        const valueLabel = document.querySelector('label[for="discountValue"]');
        const valueInput = document.getElementById('discountValue');
        const maxDiscountGroup = document.getElementById('maxDiscount').closest('.form-group');
        
        if (type === 'percentage') {
            valueLabel.innerHTML = '<i class="fas fa-percent"></i> Giá trị giảm (%)';
            valueInput.placeholder = 'Nhập % giảm (1-100)';
            valueInput.max = '100';
            maxDiscountGroup.style.display = 'flex';
        } else if (type === 'fixed') {
            valueLabel.innerHTML = '<i class="fas fa-money-bill"></i> Giá trị giảm (VNĐ)';
            valueInput.placeholder = 'Nhập số tiền giảm';
            valueInput.removeAttribute('max');
            maxDiscountGroup.style.display = 'none';
        }
    }

    // Show modal
    showModal() {
        const modal = document.getElementById('voucherModal');
        modal.classList.add('show');
        document.body.style.overflow = 'hidden';
        
        // Focus first input
        setTimeout(() => {
            document.getElementById('voucherCode').focus();
        }, 300);
    }

    // Close modal
    closeVoucherModal() {
        const modal = document.getElementById('voucherModal');
        modal.classList.remove('show');
        document.body.style.overflow = 'auto';
        this.currentEditId = null;
    }

    // Show notification
    showNotification(message, type = 'info') {
        // Create notification element
        const notification = document.createElement('div');
        notification.className = `notification notification-${type}`;
        notification.innerHTML = `
            <div class="notification-content">
                <i class="fas fa-${type === 'success' ? 'check-circle' : 'info-circle'}"></i>
                <span>${message}</span>
            </div>
        `;

        // Add styles
        notification.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            background: ${type === 'success' ? '#27ae60' : '#3498db'};
            color: white;
            padding: 1rem 1.5rem;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            z-index: 3000;
            transform: translateX(100%);
            transition: all 0.3s ease;
        `;

        document.body.appendChild(notification);

        // Animate in
        setTimeout(() => {
            notification.style.transform = 'translateX(0)';
        }, 100);

        // Auto remove
        setTimeout(() => {
            notification.style.transform = 'translateX(100%)';
            setTimeout(() => {
                if (notification.parentNode) {
                    notification.parentNode.removeChild(notification);
                }
            }, 300);
        }, 3000);
    }

    // Format currency
    formatCurrency(amount) {
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(amount);
    }

    // Format date
    formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString('vi-VN', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
        });
    }

    // Get status text
    getStatusText(status) {
        const statusTexts = {
            'active': 'Hoạt động',
            'expired': 'Hết hạn',
            'inactive': 'Tạm dừng'
        };
        return statusTexts[status] || status;
    }

    // Update voucher statuses based on expiry date
    updateVoucherStatuses() {
        const now = new Date();
        let updated = false;

        this.vouchers.forEach(voucher => {
            const expiryDate = new Date(voucher.expiryDate);
            const newStatus = expiryDate <= now ? 'expired' : 'active';
            
            if (voucher.status !== newStatus && voucher.status !== 'inactive') {
                voucher.status = newStatus;
                updated = true;
            }
        });

        if (updated) {
            this.saveVouchers();
            this.updateStats();
            this.renderTable();
        }
    }
}

// Global functions for HTML onclick events
function openAddVoucherModal() {
    voucherManager.openAddVoucherModal();
}

function closeVoucherModal() {
    voucherManager.closeVoucherModal();
}

function saveVoucher() {
    voucherManager.saveVoucher();
}

// Initialize the voucher manager
const voucherManager = new VoucherManager();

// Update voucher statuses every minute
setInterval(() => {
    voucherManager.updateVoucherStatuses();
}, 60000);

// Add some additional interactive features
document.addEventListener('DOMContentLoaded', function() {
    // Add loading animation to buttons
    document.addEventListener('click', function(e) {
        if (e.target.matches('.btn-primary, .btn-warning, .btn-danger')) {
            const btn = e.target;
            const originalText = btn.innerHTML;
            
            btn.style.opacity = '0.7';
            btn.style.transform = 'scale(0.95)';
            
            setTimeout(() => {
                btn.style.opacity = '1';
                btn.style.transform = 'scale(1)';
            }, 150);
        }
    });

    // Add smooth scrolling to any internal links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth'
                });
            }
        });
    });

    // Add keyboard navigation for table
    document.addEventListener('keydown', function(e) {
        if (e.ctrlKey && e.key === 'f') {
            e.preventDefault();
            document.getElementById('searchInput').focus();
        }
    });

    // Auto-resize textarea
    document.getElementById('description').addEventListener('input', function() {
        this.style.height = 'auto';
        this.style.height = this.scrollHeight + 'px';
    });
});