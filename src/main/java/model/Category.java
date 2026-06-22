package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 商品分类管理类
 * 用于管理系统中所有商品分类，提供分类的增删查功能
 */
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Set<String> categories;  // 使用HashSet存储分类名称，保证不重复

    public Category() {
        this.categories = new HashSet<>();
        // 预置一些常用分类
        this.categories.add("食品饮料");
        this.categories.add("日用百货");
        this.categories.add("电子产品");
        this.categories.add("服装鞋帽");
        this.categories.add("生鲜果蔬");
    }

    /**
     * 添加分类
     */
    public boolean addCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return false;
        }
        return categories.add(category.trim());
    }

    /**
     * 删除分类
     */
    public boolean removeCategory(String category) {
        return categories.remove(category);
    }

    /**
     * 检查分类是否存在
     */
    public boolean containsCategory(String category) {
        return categories.contains(category);
    }

    /**
     * 获取所有分类
     */
    public Set<String> getCategories() {
        return new HashSet<>(categories);
    }

    @Override
    public String toString() {
        return "Category{" + "categories=" + categories + '}';
    }
}