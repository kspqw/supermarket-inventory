package service;

import model.InventoryItem;

import java.util.List;

/**
 * 库存管理服务接口
 * 定义库存管理系统的所有业务操作
 */
public interface InventoryService {
    
    /**
     * 添加商品到库存
     */
    boolean addProduct(InventoryItem item);
    
    /**
     * 根据ID删除商品
     */
    boolean deleteProduct(String id);
    
    /**
     * 更新商品信息
     */
    boolean updateProduct(InventoryItem item);
    
    /**
     * 根据ID查找商品
     */
    InventoryItem findProductById(String id);
    
    /**
     * 根据名称查找商品（支持模糊查询）
     */
    List<InventoryItem> findProductsByName(String name);
    
    /**
     * 根据分类查找商品
     */
    List<InventoryItem> findProductsByCategory(String category);
    
    /**
     * 获取所有商品
     */
    List<InventoryItem> getAllProducts();
    
    /**
     * 排序商品
     * @param sortType 1:按ID, 2:按名称, 3:按单价升序, 4:按单价降序, 5:按数量升序, 6:按数量降序
     */
    List<InventoryItem> sortProducts(int sortType);
    
    /**
     * 保存数据到文件
     */
    void saveData();
    
    /**
     * 从文件加载数据
     */
    void loadData();
}