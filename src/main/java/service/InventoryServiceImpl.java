package service;

import dao.FileDataStore;
import model.InventoryItem;
import model.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 库存管理服务实现类
 * 实现所有库存管理业务逻辑，使用ArrayList存储数据
 */
public class InventoryServiceImpl implements InventoryService {
    
    private List<InventoryItem> inventory;  // 使用ArrayList存储库存数据
    private FileDataStore dataStore;

    public InventoryServiceImpl() {
        this.inventory = new ArrayList<>();
        this.dataStore = new FileDataStore();
    }

    @Override
    public boolean addProduct(InventoryItem item) {
        if (item == null || item.getProduct() == null) {
            return false;
        }
        // 检查是否已存在相同ID的商品
        for (InventoryItem existing : inventory) {
            if (existing.getProduct().getId().equals(item.getProduct().getId())) {
                return false;
            }
        }
        return inventory.add(item);
    }

    @Override
    public boolean deleteProduct(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        return inventory.removeIf(item -> item.getProduct().getId().equals(id));
    }

    @Override
    public boolean updateProduct(InventoryItem updatedItem) {
        if (updatedItem == null || updatedItem.getProduct() == null) {
            return false;
        }
        String id = updatedItem.getProduct().getId();
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getProduct().getId().equals(id)) {
                inventory.set(i, updatedItem);
                return true;
            }
        }
        return false;
    }

    @Override
    public InventoryItem findProductById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        for (InventoryItem item : inventory) {
            if (item.getProduct().getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public List<InventoryItem> findProductsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String searchName = name.trim().toLowerCase();
        return inventory.stream()
                .filter(item -> item.getProduct().getName().toLowerCase().contains(searchName))
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryItem> findProductsByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return inventory.stream()
                .filter(item -> item.getProduct().getCategory().equals(category.trim()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryItem> getAllProducts() {
        return new ArrayList<>(inventory);
    }

    @Override
    public List<InventoryItem> sortProducts(int sortType) {
        List<InventoryItem> sortedList = new ArrayList<>(inventory);
        switch (sortType) {
            case 1:
                sortedList.sort(Comparator.comparing(item -> item.getProduct().getId()));
                break;
            case 2:
                sortedList.sort(Comparator.comparing(item -> item.getProduct().getName()));
                break;
            case 3:
                sortedList.sort(Comparator.comparing(item -> item.getProduct().getPrice()));
                break;
            case 4:
                sortedList.sort((a, b) -> Double.compare(b.getProduct().getPrice(), a.getProduct().getPrice()));
                break;
            case 5:
                sortedList.sort(Comparator.comparingInt(InventoryItem::getQuantity));
                break;
            case 6:
                sortedList.sort((a, b) -> Integer.compare(b.getQuantity(), a.getQuantity()));
                break;
            default:
                break;
        }
        return sortedList;
    }

    @Override
    public void saveData() {
        dataStore.saveData(inventory);
    }

    @Override
    public void loadData() {
        List<InventoryItem> loadedData = dataStore.loadData();
        if (loadedData != null) {
            this.inventory = loadedData;
        } else {
            this.inventory = new ArrayList<>();
            // 添加一些初始测试数据
            initTestData();
        }
    }

    /**
     * 初始化测试数据
     */
    private void initTestData() {
        Product p1 = new Product("P001", "康师傅红烧牛肉面", "食品饮料", 4.5);
        Product p2 = new Product("P002", "伊利纯牛奶", "食品饮料", 3.8);
        Product p3 = new Product("P003", "华为充电器", "电子产品", 69.0);
        Product p4 = new Product("P004", "苹果", "生鲜果蔬", 5.8);
        Product p5 = new Product("P005", "纸巾", "日用百货", 12.9);
        Product p6 = new Product("P006", "T恤", "服装鞋帽", 59.0);
        Product p7 = new Product("P007", "可乐", "食品饮料", 3.0);
        Product p8 = new Product("P008", "鼠标", "电子产品", 45.0);
        
        inventory.add(new InventoryItem(p1, 100));
        inventory.add(new InventoryItem(p2, 50));
        inventory.add(new InventoryItem(p3, 30));
        inventory.add(new InventoryItem(p4, 80));
        inventory.add(new InventoryItem(p5, 120));
        inventory.add(new InventoryItem(p6, 60));
        inventory.add(new InventoryItem(p7, 200));
        inventory.add(new InventoryItem(p8, 45));
    }
}