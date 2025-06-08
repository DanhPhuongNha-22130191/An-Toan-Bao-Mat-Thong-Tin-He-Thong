function confirmDelete(productId) {
    if (confirm("Bạn có chắc chắn muốn xóa sản phẩm này không?")) {
        window.location.href = '/ATBM/admin/deleteProduct?id=' + productId;
    }
}
