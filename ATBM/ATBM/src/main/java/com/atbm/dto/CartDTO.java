package com.atbm.dto;

import com.atbm.models.CartItem;
import com.atbm.models.OrderDetail;
import com.atbm.models.Product;
import com.atbm.models.Voucher;
import com.google.gson.Gson;

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
        if (product.getStock() < quantity) {
            throw new IllegalStateException("Số lượng sản phẩm trong kho không đủ");
        }

        CartItemDTO existingItem = items.stream()
                .filter(item -> item.getProductId() == product.getProductId())
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + quantity;
            if (product.getStock() < newQuantity) {
                throw new IllegalStateException("Số lượng sản phẩm trong kho không đủ");
            }
            existingItem.setQuantity(newQuantity);
        } else {
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
        return orderDetail != null ? orderDetail.getOrderNote() : null;
    }

    public String getAddress() {
        return orderDetail != null ? orderDetail.getAddress() : null;
    }

    public String getEmail() {
        return orderDetail != null ? orderDetail.getEmail() : null;
    }

    public String getPhone() {
        return orderDetail != null ? orderDetail.getPhone() : null;
    }

    public String getFullName() {
        return orderDetail != null ? orderDetail.getFullName() : null;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public long getVoucherId() {
        return voucher != null ? voucher.getVoucherId() : 0;
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
        return getSubTotal() - getDiscount() + getShipping();
    }

    public void addVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void validateCart() {
        if (items.isEmpty()) {
            throw new IllegalStateException("Giỏ hàng trống");
        }

        for (CartItemDTO item : items) {
            if (item.getQuantity() <= 0) {
                throw new IllegalStateException("Số lượng sản phẩm không hợp lệ");
            }
            if (item.getProductPrice() <= 0) {
                throw new IllegalStateException("Giá sản phẩm không hợp lệ");
            }
        }
    }

    public double getDiscount() {
        if (voucher == null) {
            return 0;
        }
        return items.stream().mapToDouble(item -> item.getProductPrice() * item.getQuantity() * (voucher.getPercentDecrease() / 100)).sum();
    }

    public double getDiscountedPrice(double discountPercentage) {
        return items.stream().mapToDouble(item -> item.getDiscountedPrice(discountPercentage)).sum();
    }

    // Thêm phương thức getItemsJson
    public String getItemsJson() {
        try {
            return new Gson().toJson(this.items);
        } catch (Exception e) {
            System.err.println("Error converting CartDTO items to JSON: " + e.getMessage());
            return "[]";
        }
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