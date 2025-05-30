
        // Sample user data
        const users = [
            { id: 1, username: 'LeTriDuc', password: '123456', birthday: '11/6/2004', address: 'Đồng Nai', phone: '0123456789', status: 'active' },
            { id: 2, username: 'TongXuanTrung', password: '147258', birthday: '11/6/2004', address: 'Đà Nẵng', phone: '0123456789', status: 'active' },
            { id: 3, username: 'NguyenQuocTan', password: '258369', birthday: '11/6/2004', address: 'Hà Nội', phone: '0123456789', status: 'inactive' },
            { id: 4, username: 'TranNhutAnh', password: '145678', birthday: '11/6/2004', address: 'Long An', phone: '0123456789', status: 'active' },
            { id: 5, username: 'DoDucDuong', password: '145789', birthday: '11/6/2004', address: 'Đồng Tháp', phone: '0123456789', status: 'active' },
            { id: 6, username: 'NguyenQuocTan', password: '123456', birthday: '11/6/2004', address: 'Tiền Giang', phone: '0123456789', status: 'active' },
            { id: 7, username: 'LeTriDuc', password: '123456', birthday: '11/6/2004', address: 'An Giang', phone: '0123456789', status: 'inactive' },
            { id: 8, username: 'TongXuanTrung', password: '123456', birthday: '11/6/2004', address: 'Huế', phone: '0123456789', status: 'active' },
            { id: 9, username: 'NguyenQuocTan', password: '123456', birthday: '11/6/2004', address: 'Đà Nẵng', phone: '0123456789', status: 'active' },
            { id: 10, username: 'LeTriDuc', password: '123456', birthday: '11/6/2004', address: 'Hà Nội', phone: '0123456789', status: 'active' },
            { id: 11, username: 'TongXuanTrung', password: '123456', birthday: '11/6/2004', address: 'Hải Phòng', phone: '0123456789', status: 'active' },
            { id: 12, username: 'NguyenQuocTan', password: '123456', birthday: '11/6/2004', address: 'Bình Dương', phone: '0123456789', status: 'active' },
            { id: 13, username: 'NguyenQuocTan', password: '123456', birthday: '11/6/2004', address: 'Đồng Nai', phone: '0123456789', status: 'active' },
            { id: 14, username: 'NguyenQuocTan', password: '123456', birthday: '11/6/2004', address: 'Quảng Nam', phone: '0123456789', status: 'active' }
        ];

        let filteredUsers = [...users];

        // Initialize the page
        document.addEventListener('DOMContentLoaded', function() {
            renderTable();
            updateStats();
            setupEventListeners();
            animateElements();
        });

        // Generate avatar from username
        function getAvatar(username) {
            const initials = username.substring(0, 2).toUpperCase();
            return initials;
        }

        // Render table
        function renderTable() {
            const tbody = document.getElementById('userTableBody');
            tbody.innerHTML = '';

            filteredUsers.forEach((user, index) => {
                const row = document.createElement('tr');
                row.style.animationDelay = `${index * 0.1}s`;
                row.className = 'animate-fade-up';
                
                row.innerHTML = `
                    <td>
                        <div class="user-avatar-small">${getAvatar(user.username)}</div>
                    </td>
                    <td><strong>#${user.id}</strong></td>
                    <td><strong>${user.username}</strong></td>
                    <td>${user.birthday}</td>
                    <td><i class="fas fa-map-marker-alt" style="color: var(--text-light); margin-right: 0.5rem;"></i>${user.address}</td>
                    <td><i class="fas fa-phone" style="color: var(--text-light); margin-right: 0.5rem;"></i>${user.phone}</td>
                    <td>
                        <span class="status-badge ${user.status === 'active' ? 'status-active' : 'status-inactive'}">
                            <i class="fas fa-circle" style="font-size: 0.5rem; margin-right: 0.3rem;"></i>
                            ${user.status === 'active' ? 'Hoạt động' : 'Không hoạt động'}
                        </span>
                    </td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn btn-sm btn-outline tooltip" data-tooltip="Chỉnh sửa" onclick="editUser(${user.id})">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn btn-sm btn-danger tooltip" data-tooltip="Xóa" onclick="deleteUser(${user.id})">
                                <i class="fas fa-trash"></i>
                            </button>
                        </div>
                    </td>
                `;
                
                tbody.appendChild(row);
            });
        }

        // Update statistics
        function updateStats() {
            const totalUsers = users.length;
            const activeUsers = users.filter(user => user.status === 'active').length;
            const uniqueLocations = [...new Set(users.map(user => user.address))].length;
            const newUsers = Math.floor(Math.random() * 5) + 1; // Simulated new users

            animateCounter('totalUsers', totalUsers);
            animateCounter('activeUsers', activeUsers);
            animateCounter('newUsers', newUsers);
            animateCounter('locations', uniqueLocations);
        }

        // Animate counter
        function animateCounter(elementId, targetValue) {
            const element = document.getElementById(elementId);
            let currentValue = 0;
            const increment = targetValue / 30;
            
            const timer = setInterval(() => {
                currentValue += increment;
                if (currentValue >= targetValue) {
                    currentValue = targetValue;
                    clearInterval(timer);
                }
                element.textContent = Math.floor(currentValue);
            }, 50);
        }

        // Setup event listeners
        function setupEventListeners() {
            const searchInput = document.getElementById('searchInput');
            const filterType = document.getElementById('filterType');

            searchInput.addEventListener('input', debounce(handleSearch, 300));
            filterType.addEventListener('change', handleSearch);
        }

        // Debounce function for search
        function debounce(func, wait) {
            let timeout;
            return function executedFunction(...args) {
                const later = () => {
                    clearTimeout(timeout);
                    func(...args);
                };
                clearTimeout(timeout);
                timeout = setTimeout(later, wait);
            };
        }

        // Handle search and filter
        function handleSearch() {
            const searchTerm = document.getElementById('searchInput').value.toLowerCase();
            const filterType = document.getElementById('filterType').value;

            filteredUsers = users.filter(user => {
                let searchField = '';
                switch(filterType) {
                    case 'name':
                        searchField = user.username.toLowerCase();
                        break;
                    case 'address':
                        searchField = user.address.toLowerCase();
                        break;
                    case 'phone':
                        searchField = user.phone.toLowerCase();
                        break;
                    default:
                        searchField = user.username.toLowerCase();
                }
                return searchField.includes(searchTerm);
            });

            renderTable();
        }

        // Add user function
        function addUser() {
            const form = document.getElementById('addUserForm');
            const formData = {
                username: document.getElementById('username').value.trim(),
                password: document.getElementById('password').value,
                birthday: document.getElementById('birthday').value,
                address: document.getElementById('address').value,
                phone: document.getElementById('phone').value.trim()
            };

            // Validate form
            if (!validateAddUserForm(formData)) {
                return;
            }

            // Generate new ID
            const newId = Math.max(...users.map(u => u.id)) + 1;

            // Create new user
            const newUser = {
                id: newId,
                username: formData.username,
                password: formData.password,
                birthday: formatDate(formData.birthday),
                address: formData.address,
                phone: formData.phone,
                status: 'active'
            };

            // Add to users array
            users.push(newUser);
            filteredUsers = [...users];

            // Reset form and close modal
            form.reset();
            closeAddUserModal();

            // Update UI
            renderTable();
            updateStats();

            // Show success message
            showNotification(`Thêm người dùng "${newUser.username}" thành công!`, 'success');

            // Highlight new row
            setTimeout(() => {
                const newRow = document.querySelector(`tbody tr:last-child`);
                if (newRow) {
                    newRow.style.background = 'linear-gradient(135deg, rgba(72, 187, 120, 0.1), rgba(72, 187, 120, 0.05))';
                    newRow.scrollIntoView({ behavior: 'smooth', block: 'center' });
                }
            }, 100);
        }

        // Validate add user form
        function validateAddUserForm(data) {
            let isValid = true;

            // Reset previous errors
            document.querySelectorAll('.form-error').forEach(error => {
                error.classList.remove('show');
            });
            document.querySelectorAll('.form-input').forEach(input => {
                input.classList.remove('error');
            });

            // Username validation
            if (!data.username) {
                showFormError('username', 'Tên đăng nhập không được để trống');
                isValid = false;
            } else if (users.some(u => u.username.toLowerCase() === data.username.toLowerCase())) {
                showFormError('username', 'Tên đăng nhập đã tồn tại');
                isValid = false;
            } else if (data.username.length < 3) {
                showFormError('username', 'Tên đăng nhập phải có ít nhất 3 ký tự');
                isValid = false;
            }

            // Password validation
            if (!data.password) {
                showFormError('password', 'Mật khẩu không được để trống');
                isValid = false;
            } else if (data.password.length < 6) {
                showFormError('password', 'Mật khẩu phải có ít nhất 6 ký tự');
                isValid = false;
            }

            // Birthday validation
            if (!data.birthday) {
                showFormError('birthday', 'Vui lòng chọn ngày sinh');
                isValid = false;
            } else {
                const birthDate = new Date(data.birthday);
                const today = new Date();
                const age = today.getFullYear() - birthDate.getFullYear();
                if (age < 13 || age > 100) {
                    showFormError('birthday', 'Tuổi phải từ 13 đến 100');
                    isValid = false;
                }
            }

            // Address validation
            if (!data.address) {
                showFormError('address', 'Vui lòng chọn địa chỉ');
                isValid = false;
            }

            // Phone validation
            if (!data.phone) {
                showFormError('phone', 'Số điện thoại không được để trống');
                isValid = false;
            } else if (!/^0\d{9}$/.test(data.phone)) {
                showFormError('phone', 'Số điện thoại phải có 10 số và bắt đầu bằng 0');
                isValid = false;
            } else if (users.some(u => u.phone === data.phone)) {
                showFormError('phone', 'Số điện thoại đã được sử dụng');
                isValid = false;
            }

            return isValid;
        }

        // Show form error
        function showFormError(fieldName, message) {
            const input = document.getElementById(fieldName);
            const error = document.getElementById(fieldName + 'Error');
            
            input.classList.add('error');
            error.textContent = message;
            error.classList.add('show');
            
            // Add shake animation
            input.style.animation = 'shake 0.5s ease-in-out';
            setTimeout(() => {
                input.style.animation = '';
            }, 500);
        }

        // Format date
        function formatDate(dateString) {
            const date = new Date(dateString);
            return `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`;
        }

        // Open add user modal
        function openAddUserModal() {
            const modal = document.getElementById('addUserModal');
            modal.classList.add('active');
            document.body.style.overflow = 'hidden';
            
            // Focus first input
            setTimeout(() => {
                document.getElementById('username').focus();
            }, 300);
        }

        // Close add user modal
        function closeAddUserModal() {
            const modal = document.getElementById('addUserModal');
            modal.classList.remove('active');
            document.body.style.overflow = '';
            
            // Reset form
            document.getElementById('addUserForm').reset();
            
            // Clear errors
            document.querySelectorAll('.form-error').forEach(error => {
                error.classList.remove('show');
            });
            document.querySelectorAll('.form-input').forEach(input => {
                input.classList.remove('error');
            });
        }

        // Close modal when clicking outside
        document.addEventListener('click', function(e) {
            if (e.target.classList.contains('modal-overlay')) {
                closeAddUserModal();
            }
        });

        // Close modal with Escape key
        document.addEventListener('keydown', function(e) {
            if (e.key === 'Escape') {
                closeAddUserModal();
            }
        });

        // Add shake animation CSS
        const shakeStyle = document.createElement('style');
        shakeStyle.textContent = `
            @keyframes shake {
                0%, 100% { transform: translateX(0); }
                25% { transform: translateX(-5px); }
                75% { transform: translateX(5px); }
            }
        `;
        document.head.appendChild(shakeStyle);
        function editUser(userId) {
            const user = users.find(u => u.id === userId);
            if (user) {
                // Create a simple modal-like alert for demo
                const newUsername = prompt('Nhập tên đăng nhập mới:', user.username);
                const newAddress = prompt('Nhập địa chỉ mới:', user.address);
                const newPhone = prompt('Nhập số điện thoại mới:', user.phone);
                
                if (newUsername && newAddress && newPhone) {
                    user.username = newUsername;
                    user.address = newAddress;
                    user.phone = newPhone;
                    
                    // Show success message
                    showNotification('Cập nhật thông tin thành công!', 'success');
                    renderTable();
                    updateStats();
                }
            }
        }

        // Delete user function
        function deleteUser(userId) {
            const user = users.find(u => u.id === userId);
            if (user && confirm(`Bạn có chắc chắn muốn xóa người dùng "${user.username}"?`)) {
                const index = users.findIndex(u => u.id === userId);
                if (index > -1) {
                    users.splice(index, 1);
                    filteredUsers = filteredUsers.filter(u => u.id !== userId);
                    
                    // Add delete animation
                    const row = document.querySelector(`tr:nth-child(${index + 1})`);
                    if (row) {
                        row.style.transform = 'translateX(-100%)';
                        row.style.opacity = '0';
                        setTimeout(() => {
                            renderTable();
                            updateStats();
                        }, 300);
                    }
                    
                    showNotification('Xóa người dùng thành công!', 'success');
                }
            }
        }

        // Show notification
        function showNotification(message, type = 'info') {
            // Create notification element
            const notification = document.createElement('div');
            notification.style.cssText = `
                position: fixed;
                top: 20px;
                right: 20px;
                padding: 1rem 1.5rem;
                background: ${type === 'success' ? 'var(--success-color)' : 'var(--primary-color)'};
                color: white;
                border-radius: var(--border-radius);
                box-shadow: var(--shadow-lg);
                z-index: 1000;
                transform: translateX(100%);
                transition: var(--transition);
                font-weight: 600;
                display: flex;
                align-items: center;
                gap: 0.5rem;
            `;
            
            notification.innerHTML = `
                <i class="fas fa-${type === 'success' ? 'check-circle' : 'info-circle'}"></i>
                ${message}
            `;
            
            document.body.appendChild(notification);
            
            // Animate in
            setTimeout(() => {
                notification.style.transform = 'translateX(0)';
            }, 100);
            
            // Remove after 3 seconds
            setTimeout(() => {
                notification.style.transform = 'translateX(100%)';
                setTimeout(() => {
                    document.body.removeChild(notification);
                }, 300);
            }, 3000);
        }

        // Animate elements on load
        function animateElements() {
            const elements = document.querySelectorAll('.animate-fade-up, .animate-slide-left');
            elements.forEach((element, index) => {
                element.style.animationDelay = `${index * 0.1}s`;
            });
        }

        // Mobile sidebar toggle
        function toggleSidebar() {
            const sidebar = document.querySelector('.sidebar');
            sidebar.classList.toggle('open');
        }

        // Add click handlers for nav links
        document.addEventListener('DOMContentLoaded', function() {
            const navLinks = document.querySelectorAll('.nav-link');
            navLinks.forEach(link => {
                link.addEventListener('click', function(e) {
                    e.preventDefault();
                    
                    // Remove active class from all links
                    navLinks.forEach(l => l.classList.remove('active'));
                    
                    // Add active class to clicked link
                    this.classList.add('active');
                    
                    // Add click animation
                    this.style.transform = 'scale(0.95)';
                    setTimeout(() => {
                        this.style.transform = '';
                    }, 150);
                });
            });
        });

        // Add hover effect to stat cards
        document.addEventListener('DOMContentLoaded', function() {
            const statCards = document.querySelectorAll('.stat-card');
            statCards.forEach((card, index) => {
                card.addEventListener('mouseenter', function() {
                    this.style.transform = 'translateY(-8px) scale(1.02)';
                });
                
                card.addEventListener('mouseleave', function() {
                    this.style.transform = 'translateY(-4px) scale(1)';
                });
            });
        });

        // Add smooth scrolling for better UX
        document.documentElement.style.scrollBehavior = 'smooth';

        // Add keyboard shortcuts
        document.addEventListener('keydown', function(e) {
            // Ctrl + F to focus search
            if (e.ctrlKey && e.key === 'f') {
                e.preventDefault();
                document.getElementById('searchInput').focus();
            }
            
            // Escape to clear search
            if (e.key === 'Escape') {
                const searchInput = document.getElementById('searchInput');
                if (searchInput.value) {
                    searchInput.value = '';
                    handleSearch();
                }
            }
        });

        // Add loading state for actions
        function showLoading(button) {
            const originalContent = button.innerHTML;
            button.innerHTML = '<div class="loading"></div>';
            button.disabled = true;
            
            setTimeout(() => {
                button.innerHTML = originalContent;
                button.disabled = false;
            }, 1000);
        }

        // Enhanced table row interactions
        document.addEventListener('DOMContentLoaded', function() {
            // Add click-to-select functionality
            document.addEventListener('click', function(e) {
                if (e.target.closest('tbody tr')) {
                    const row = e.target.closest('tbody tr');
                    const allRows = document.querySelectorAll('tbody tr');
                    
                    // Remove selection from other rows
                    allRows.forEach(r => r.style.background = '');
                    
                    // Highlight selected row
                    row.style.background = 'linear-gradient(135deg, rgba(102, 126, 234, 0.1), rgba(240, 147, 251, 0.1))';
                }
            });
        });

        // Auto-refresh data every 30 seconds (simulated)
        setInterval(() => {
            // Simulate new user joining
            if (Math.random() > 0.8) {
                updateStats();
                showNotification('Có người dùng mới tham gia!', 'info');
            }
        }, 30000);

        // Add export functionality
        function exportUsers() {
            const csvContent = "data:text/csv;charset=utf-8," 
                + "ID,Tên đăng nhập,Ngày sinh,Địa chỉ,Số điện thoại,Trạng thái\n"
                + users.map(user => 
                    `${user.id},${user.username},${user.birthday},${user.address},${user.phone},${user.status}`
                ).join("\n");
            
            const encodedUri = encodeURI(csvContent);
            const link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "users_export.csv");
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            
            showNotification('Xuất dữ liệu thành công!', 'success');
        }

        // Add print functionality
        function printTable() {
            const printWindow = window.open('', '_blank');
            printWindow.document.write(`
                <html>
                <head>
                    <title>Danh sách người dùng</title>
                    <style>
                        body { font-family: Arial, sans-serif; }
                        table { width: 100%; border-collapse: collapse; }
                        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
                        th { background-color: #f2f2f2; }
                        h1 { color: #333; text-align: center; }
                    </style>
                </head>
                <body>
                    <h1>Danh sách người dùng</h1>
                    <p>Ngày in: ${new Date().toLocaleDateString('vi-VN')}</p>
                    ${document.querySelector('#userTable').outerHTML}
                </body>
                </html>
            `);
            printWindow.document.close();
            printWindow.print();
        }

        console.log('🎉 Admin Dashboard loaded successfully!');
        console.log('📊 Total users:', users.length);
        console.log('🔍 Search functionality enabled');
        console.log('⚡ Real-time updates enabled');
 