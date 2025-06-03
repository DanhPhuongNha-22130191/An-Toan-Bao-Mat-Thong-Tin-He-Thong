package com.atbm.dto;

import com.atbm.models.CartItem;
import com.atbm.models.OrderDetail;
import com.atbm.models.Product;
import com.atbm.models.Voucher;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CartDTO {
    private List<CartItemDTO> items;
    private Voucher voucher;
    private OrderDetail orderDetail;

    public CartDTO() {
        items = new LinkedList<>();
    }

    public String getProductInfo() {
        StringBuilder builder = new StringBuilder();
        for (CartItemDTO item : items) {
            builder.append(item.getProductName()).append(" - ").append(item.getQuantity()).append(" - ");
        }
        builder.append(voucher == null ? "none" : voucher.getCode());
        return builder.toString();
    }

    public CartItem add(Product product, long cartItemId, int quantity) {
        // Kiểm tra tồn kho
        if (product.getStock() < quantity) {
            throw new IllegalStateException("Số lượng sản phẩm trong kho không đủ");
        }

        // Kiểm tra sản phẩm đã có trong giỏ hàng chưa
        CartItemDTO existingItem = items.stream()
                .filter(item -> item.getProductId() == product.getProductId())
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            // Cập nhật số lượng nếu sản phẩm đã tồn tại
            int newQuantity = existingItem.getQuantity() + quantity;
            if (product.getStock() < newQuantity) {
                throw new IllegalStateException("Số lượng sản phẩm trong kho không đủ");
            }
            existingItem.setQuantity(newQuantity);
        } else {
            // Thêm sản phẩm mới vào giỏ hàng
            CartItemDTO newItem = new CartItemDTO(
                    cartItemId,
                    product.getProductId(),
                    product.getName(),
                    product.getImage(),
                    product.getPrice(),
                    quantity
            );
            items.add(newItem);
        }
        return new CartItem(cartItemId, product.getProductId(), quantity);
    }

    public CartItem updateQuantity(long productId, int quantity) {
        CartItemDTO item = items.stream()
                .filter(i -> i.getProductId() == productId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại trong giỏ hàng"));

        if (quantity <= 0) {
            items.remove(item);
        } else {
            item.setQuantity(quantity);
        }
        return new CartItem(item.getCartItemId(), item.getProductId(), item.getQuantity());
    }

    public CartItemDTO removeItem(long productId) {
        Iterator<CartItemDTO> iterator = items.iterator();
        while (iterator.hasNext()) {
            CartItemDTO item = iterator.next();
            if (item.getProductId() == productId) {
                iterator.remove();
                return item;
            }
        }
        return null;
    }


    public void clear() {
        items.clear();
        voucher = null;
        orderDetail = null;
    }


    public double getShipping() {
        double subtotal = getSubTotal();
        if (subtotal >= 5000000) {
            return 0;
        } else if (subtotal >= 2000000) {
            return 30000;
        } else {
            return 50000;
        }
    }

    public String getOrderNote() {
        return orderDetail.getOrderNote();
    }

    public String getAddress() {
        return orderDetail.getAddress();
    }

    public String getEmail() {
        return orderDetail.getEmail();
    }

    public String getPhone() {
        return orderDetail.getPhone();
    }

    public String getFullName() {
        return orderDetail.getFullName();
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public long getVoucherId() {
        return voucher.getVoucherId();
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void addAll(List<CartItemDTO> listItems) {
        items.addAll(listItems);
    }

    public double getSubTotal() {
        return items.stream().mapToDouble(CartItemDTO::getTotalPrice).sum();
    }

    public double getTotalPrice() {
        double subtotal = getSubTotal() - getDiscount();
        return subtotal + getShipping();
    }


    public void addVoucher(Voucher voucher) {
        this.voucher = voucher;
    }


    public boolean isEmpty() {
        return items.isEmpty();
    }


    public void validateCart() {
        // Kiểm tra giỏ hàng trống
        if (items.isEmpty()) {
            throw new IllegalStateException("Giỏ hàng trống");
        }

        // Kiểm tra từng sản phẩm trong giỏ hàng
        for (CartItemDTO item : items) {
            // Kiểm tra số lượng
            if (item.getQuantity() <= 0) {
                throw new IllegalStateException("Số lượng sản phẩm không hợp lệ");
            }


            // Kiểm tra giá
            if (item.getProductPrice() <= 0) {
                throw new IllegalStateException("Giá sản phẩm không hợp lệ");
            }
        }

    }

    public double getDiscount() {
        if (voucher == null)
            return 0;
        return getDiscountedPrice(voucher.getPercentDecrease());
    }

    public double getDiscountedPrice(double discountPercentage) {
        return items.stream().mapToDouble(item -> item.getDiscountedPrice(discountPercentage)).sum();
    }

    public class CartItemDTO {
        private long cartItemId;
        private long productId;
        private String productName;
        private String productImg;
        private double productPrice;
        private int quantity;

        public CartItemDTO() {
        }

        public CartItemDTO(long cartItemId, long productId, String productName, String productImg,
                           double productPrice, int quantity) {
            this.cartItemId = cartItemId;
            this.productId = productId;
            this.productName = productName;
            this.productImg = productImg;
            this.productPrice = productPrice;
            this.quantity = quantity;
        }

        public long getCartItemId() {
            return cartItemId;
        }

        public void setCartItemId(long cartItemId) {
            this.cartItemId = cartItemId;
        }

        public double getTotalPrice() {
            return quantity * productPrice;
        }

        public long getProductId() {
            return productId;
        }

        public void setProductId(long productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImg() {
            return productImg;
        }

        public void setProductImg(String productImg) {
            this.productImg = productImg;
        }

        public double getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(double productPrice) {
            this.productPrice = productPrice;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }


        public double getDiscountedPrice(double discountPercentage) {
            return productPrice * (1 - discountPercentage / 100);
        }

    }
}


