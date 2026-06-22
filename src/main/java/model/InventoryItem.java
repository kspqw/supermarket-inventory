package model;

import java.io.Serializable;

/**
 * 库存项类
 * 包含商品信息和库存数量，是库存管理的基本单位
 */
public class InventoryItem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Product product;   // 商品信息
    private int quantity;      // 库存数量

    public InventoryItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("库存数量不能为负数！");
        }
        this.quantity = quantity;
    }

    /**
     * 增加库存
     */
    public void addQuantity(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("增加数量不能为负数！");
        }
        this.quantity += amount;
    }

    /**
     * 减少库存
     */
    public boolean reduceQuantity(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("减少数量不能为负数！");
        }
        if (this.quantity < amount) {
            return false;  // 库存不足
        }
        this.quantity -= amount;
        return true;
    }

    /**
     * 计算该库存项的总价值
     */
    public double getTotalValue() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("库存项 {商品ID=%s, 名称=%s, 分类=%s, 单价=%.2f, 数量=%d, 总价值=%.2f}",
                product.getId(), product.getName(), product.getCategory(),
                product.getPrice(), quantity, getTotalValue());
    }
}