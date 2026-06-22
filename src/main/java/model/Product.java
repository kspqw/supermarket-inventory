package model;

import java.io.Serializable;
import java.util.UUID;

/**
 * 商品实体类
 * 封装商品的基本属性：ID、名称、分类、单价
 * 体现面向对象封装特性
 */
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;          // 商品ID（唯一标识）
    private String name;        // 商品名称
    private String category;    // 商品分类
    private double price;       // 商品单价

    /**
     * 全参构造函数
     */
    public Product(String id, String name, String category, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    /**
     * 自动生成ID的构造函数
     */
    public Product(String name, String category, double price) {
        this.id = generateId();
        this.name = name;
        this.category = category;
        this.price = price;
    }

    /**
     * 生成唯一ID
     */
    private static String generateId() {
        return "P" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Getter 和 Setter 方法
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Product{id='%s', name='%s', category='%s', price=%.2f}", 
                id, name, category, price);
    }
}