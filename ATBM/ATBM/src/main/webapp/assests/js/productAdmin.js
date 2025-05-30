// Sample product data
let products = [
    {
        id: 'SP001',
        name: 'iPhone 15 Pro Max',
        category: 'Điện thoại',
        price: 29990000,
        quantity: 25,
        status: 'active',
        image: 'https://via.placeholder.com/150x150/667eea/ffffff?text=iPhone',
        description: 'iPhone 15 Pro Max với chip A17 Pro mạnh mẽ'
    },
    {
        id: 'SP002',
        name: 'Samsung Galaxy S24 Ultra',
        category: 'Điện thoại',
        price: 26990000,
        quantity: 18,
        status: 'active',
        image: 'https://via.placeholder.com/150x150/764ba2/ffffff?text=Samsung',
        description: 'Galaxy S24 Ultra với bút S Pen tích hợp'
    },
    {
        id: 'SP003',
        name: 'MacBook Pro M3',
        category: 'Laptop',
        price: 45990000,
        quantity: 12,
        status: 'active',
        image: 'https://via.placeholder.com/150x150/4facfe/ffffff?text=MacBook',
        description: 'MacBook Pro với chip M3 hiệu năng cao'
    },
    {
        id: 'SP004',
        name: 'Dell XPS 13',
        category: 'Laptop',
        price: 32990000,
        quantity: 8,
        status: 'active',
        image: 'https://via.placeholder.com/150x150/ff6b6b/ffffff?text=Dell',
        description: 'Laptop Dell XPS 13 siêu mỏng nhẹ'
    },
    {
        id: 'SP005',
        name: 'iPad Air M2',
        category: 'Tablet',
        price: 18990000,
        quantity: 15,
        status: 'active',
        image: 'https://via.placeholder.com/150x150/a8e6cf/ffffff?text=iPad',
        description: 'iPad Air với chip M2 mạnh mẽ'
    },
    {
        id: 'SP006',
        name: 'AirPods Pro 2',
        category: 'Âm thanh',
        price: 6490000,
        quantity: 5,
        status: 'active',
        image: 'https://via.placeholder.com/150x150/ffd93d/ffffff?text=AirPods',
        description: 'AirPods Pro thế hệ 2 với chống ồn chủ động'
    },
    {
        id: 'SP007',
        name: 'Ốp lưng iPhone 15',
        category: 'Phụ kiện',
        price: 890000,
        quantity: 0,
        status: 'out-of-stock',
        image: 'https://via.placeholder.com/150x150/88d8c0/ffffff?text=Case',
        description: 'Ốp lưng silicon cho iPhone 15'
    },
    {
        id: 'SP008',
        name: 'Sony WH-1000XM5',
        category: 'Âm thanh',
        price: 8990000,
        quantity: 3,
        status: 'active',
        image: 'https://via.placeholder.com/150x150/667eea/ffffff?text=Sony',
        description: 'Tai nghe Sony chống ồn cao cấp'
    }
];

let filteredProducts = [...products];

// DOM Elements
const searchInput = document.getElementById('searchInput');
const filterCategory = document.getElementById('filterCategory');
const filterStatus = document.getElementById('filterStatus');
const productTableBody = document.getElementById('productTableBody');
const addProductModal = document.getElementById('addProductModal');
const addProductForm = document.getElementById('addProductForm');

// Initialize page
document.addEventListener('DOMContentLoaded', function() {
    updateStats();
    renderProducts();
    setupEventListeners();
});

// Setup event listeners
function setupEventListeners() {
    searchInput.addEventListener('input', filterProducts);
    filterCategory.addEventListener('change', filterProducts);
    filterStatus.addEventListener('change', filterProducts);
    
    // Image preview
    document.getElementById('image').addEventListener('input', function() {
        const imageUrl = this.value;
        const preview = document.getElementById('imagePreview');
        const previewImg = document.getElementById('previewImg');
        
        if (imageUrl) {
            previewImg.src = imageUrl;
            preview.style.display = 'block';
        } else {
            preview.style.display = 'none';
        }
    });

    // Modal close on overlay click
    addProductModal.addEventListener('click', function(e) {
        if (e.target === addProductModal) {
            closeAddProductModal();
        }
    });
}

// Update statistics
function updateStats() {
    const totalProducts = products.length;
    const activeProducts = products.filter(p => p.status === 'active').length;
    const lowStock = products.filter(p => p.quantity <= 5 && p.quantity > 0).length;
    const categories = [...new Set(products.map(p => p.category))].length;

    document.getElementById('totalProducts').textContent = totalProducts;
    document.getElementById('activeProducts').textContent = activeProducts;
    document.getElementById('lowStock').textContent = lowStock;
    document.getElementById('categories').textContent = categories;
}

// Filter products
function filterProducts() {
    const searchTerm = searchInput.value.toLowerCase();
    const categoryFilter = filterCategory.value;
    const statusFilter = filterStatus.value;

    filteredProducts = products.filter(product => {
        const matchesSearch = product.name.toLowerCase().includes(searchTerm) ||
                             product.id.toLowerCase().includes(searchTerm);
        const matchesCategory = !categoryFilter || product.category === categoryFilter;
        const matchesStatus = !statusFilter || product.status === statusFilter;

        return matchesSearch && matchesCategory && matchesStatus;
    });

    renderProducts();
}

// Render products table
function renderProducts() {
    if (filteredProducts.length === 0) {
        productTableBody.innerHTML = `
            <tr>
                <td colspan="8" style="text-align: center; padding: 2rem; color: #666;">
                    <i class="fas fa-box-open" style="font-size: 3rem; margin-bottom: 1rem; opacity: 0.5;"></i>
                    <br>Không tìm thấy sản phẩm nào
                </td>
            </tr>
        `;
        return;
    }

    productTableBody.innerHTML = filteredProducts.map(product => `
        <tr>
            <td>
                ${product.image ? 
                    `<img src="${product.image}" alt="${product.name}" class="product-image" onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
                     <div class="product-placeholder" style="display: none;">
                         <i class="fas fa-image"></i>
                     </div>` :
                    `<div class="product-placeholder">
                         <i class="fas fa-image"></i>
                     </div>`
                }
            </td>
            <td><strong>${product.id}</strong></td>
            <td>
                <div style="font-weight: 600; margin-bottom: 0.25rem;">${product.name}</div>
                <div style="font-size: 0.8rem; color: #666;">${product.description || 'Không có mô tả'}</div>
            </td>
            <td>
                <span class="badge" style="background: rgba(102, 126, 234, 0.2); color: #667eea; padding: 0.25rem 0.75rem; border-radius: 15px; font-size: 0.8rem;">
                    ${product.category}
                </span>
            </td>
            <td class="price">${formatPrice(product.price)}</td>
            <td class="${product.quantity <= 5 && product.quantity > 0 ? 'quantity-low' : ''}">
                ${product.quantity}
                ${product.quantity <= 5 && product.quantity > 0 ? '<i class="fas fa-exclamation-triangle" style="margin-left: 0.5rem;"></i>' : ''}
            </td>
            <td>
                <span class="status ${product.status}">
                    ${getStatusText(product.status)}
                </span>
            </td>
            <td>
                <button class="btn btn-success" style="padding: 0.5rem; margin-right: 0.5rem;" onclick="editProduct('${product.id}')" title="Chỉnh sửa">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn btn-danger" style="padding: 0.5rem;" onclick="deleteProduct('${product.id}')" title="Xóa">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        </tr>
    `).join('');
}

// Format price
function formatPrice(price) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(price);
}

// Get status text
function getStatusText(status) {
    const statusMap = {
        'active': 'Đang bán',
        'inactive': 'Tạm dừng',
        'out-of-stock': 'Hết hàng'
    };
    return statusMap[status] || status;
}

// Generate product ID
function generateProductId() {
    const lastId = products.length > 0 ? 
        Math.max(...products.map(p => parseInt(p.id.replace('SP', '')))) : 0;
    return `SP${String(lastId + 1).padStart(3, '0')}`;
}

// Open add product modal
function openAddProductModal() {
    addProductModal.classList.add('active');
    document.body.style.overflow = 'hidden';
    resetForm();
}

// Close add product modal
function closeAddProductModal() {
    addProductModal.classList.remove('active');
    document.body.style.overflow = 'auto';
    resetForm();
}

// Reset form
function resetForm() {
    addProductForm.reset();
    document.getElementById('imagePreview').style.display = 'none';
    
    // Hide all error messages
    document.querySelectorAll('.form-error').forEach(error => {
        error.classList.remove('show');
    });
    
    // Reset form validation styles
    document.querySelectorAll('.form-input').forEach(input => {
        input.style.borderColor = 'rgba(0, 0, 0, 0.1)';
    });
}

// Validate form
function validateForm() {
    let isValid = true;
    
    const fields = [
        { id: 'productName', errorId: 'productNameError', message: 'Tên sản phẩm không được để trống' },
        { id: 'category', errorId: 'categoryError', message: 'Vui lòng chọn danh mục' },
        { id: 'price', errorId: 'priceError', message: 'Giá sản phẩm phải lớn hơn 0' },
        { id: 'quantity', errorId: 'quantityError', message: 'Số lượng phải lớn hơn hoặc bằng 0' }
    ];
    
    fields.forEach(field => {
        const input = document.getElementById(field.id);
        const error = document.getElementById(field.errorId);
        
        let fieldValid = true;
        
        if (field.id === 'price') {
            fieldValid = input.value && parseFloat(input.value) > 0;
        } else if (field.id === 'quantity') {
            fieldValid = input.value !== '' && parseInt(input.value) >= 0;
        } else {
            fieldValid = input.value.trim() !== '';
        }
        
        if (!fieldValid) {
            error.classList.add('show');
            input.style.borderColor = '#ff6b6b';
            isValid = false;
        } else {
            error.classList.remove('show');
            input.style.borderColor = 'rgba(0, 0, 0, 0.1)';
        }
    });
    
    return isValid;
}

// Add product
function addProduct() {
    if (!validateForm()) {
        return;
    }
    
    const formData = new FormData(addProductForm);
    const productData = {
        id: generateProductId(),
        name: document.getElementById('productName').value.trim(),
        category: document.getElementById('category').value,
        price: parseFloat(document.getElementById('price').value),
        quantity: parseInt(document.getElementById('quantity').value),
        status: parseInt(document.getElementById('quantity').value) > 0 ? 'active' : 'out-of-stock',
        image: document.getElementById('image').value.trim(),
        description: document.getElementById('description').value.trim()
    };
    
    products.unshift(productData);
    filteredProducts = [...products];
    
    updateStats();
    renderProducts();
    closeAddProductModal();
    
    // Show success message
    showNotification('Thêm sản phẩm thành công!', 'success');
}

// Edit product
function editProduct(productId) {
    const product = products.find(p => p.id === productId);
    if (!product) return;
    
    // Fill form with product data
    document.getElementById('productName').value = product.name;
    document.getElementById('category').value = product.category;
    document.getElementById('price').value = product.price;
    document.getElementById('quantity').value = product.quantity;
    document.getElementById('description').value = product.description || '';
    document.getElementById('image').value = product.image || '';
    
    // Show image preview if exists
    if (product.image) {
        document.getElementById('previewImg').src = product.image;
        document.getElementById('imagePreview').style.display = 'block';
    }
    
    // Change modal title and button
    document.querySelector('.modal-title').textContent = 'Chỉnh sửa sản phẩm';
    const addButton = document.querySelector('.modal-footer .btn-primary');
    addButton.innerHTML = '<i class="fas fa-save"></i> Cập nhật';
    addButton.onclick = () => updateProduct(productId);
    
    openAddProductModal();
}

// Update product
function updateProduct(productId) {
    if (!validateForm()) {
        return;
    }
    
    const productIndex = products.findIndex(p => p.id === productId);
    if (productIndex === -1) return;
    
    const updatedProduct = {
        ...products[productIndex],
        name: document.getElementById('productName').value.trim(),
        category: document.getElementById('category').value,
        price: parseFloat(document.getElementById('price').value),
        quantity: parseInt(document.getElementById('quantity').value),
        status: parseInt(document.getElementById('quantity').value) > 0 ? 'active' : 'out-of-stock',
        image: document.getElementById('image').value.trim(),
        description: document.getElementById('description').value.trim()
    };
    
    products[productIndex] = updatedProduct;
    filteredProducts = [...products];
    
    updateStats();
    renderProducts();
    closeAddProductModal();
    
    // Reset modal
    document.querySelector('.modal-title').textContent = 'Thêm sản phẩm mới';
    const addButton = document.querySelector('.modal-footer .btn-primary');
    addButton.innerHTML = '<i class="fas fa-plus"></i> Thêm sản phẩm';
    addButton.onclick = addProduct;
    
    showNotification('Cập nhật sản phẩm thành công!', 'success');
}

// Delete product
function deleteProduct(productId) {
    const product = products.find(p => p.id === productId);
    if (!product) return;
    
    if (confirm(`Bạn có chắc chắn muốn xóa sản phẩm "${product.name}"?`)) {
        products = products.filter(p => p.id !== productId);
        filteredProducts = [...products];
        
        updateStats();
        renderProducts();
        
        showNotification('Xóa sản phẩm thành công!', 'success');
    }
}

// Show notification
function showNotification(message, type = 'info') {
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.style.cssText = `
        position: fixed;
        top: 2rem;
        right: 2rem;
        background: ${type === 'success' ? 'linear-gradient(135deg, #4caf50, #45a049)' : 
                    type === 'error' ? 'linear-gradient(135deg, #ff6b6b, #ff5252)' : 
                    'linear-gradient(135deg, #667eea, #764ba2)'};
        color: white;
        padding: 1rem 2rem;
        border-radius: 12px;
        z-index: 3000;
        box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
        animation: slideInRight 0.3s ease;
        font-weight: 600;
    `;
    
    notification.innerHTML = `
        <i class="fas fa-${type === 'success' ? 'check-circle' : 
                           type === 'error' ? 'exclamation-circle' : 
                           'info-circle'}"></i>
        ${message}
    `;
    
    document.body.appendChild(notification);
    
    setTimeout(() => {
        notification.style.animation = 'slideOutRight 0.3s ease';
        setTimeout(() => {
            document.body.removeChild(notification);
        }, 300);
    }, 3000);
}

// Add CSS for notifications
const style = document.createElement('style');
style.textContent = `
    @keyframes slideInRight {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
    
    @keyframes slideOutRight {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(100%);
            opacity: 0;
        }
    }
`;
document.head.appendChild(style);