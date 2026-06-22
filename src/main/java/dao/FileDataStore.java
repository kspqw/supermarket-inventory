package dao;

import model.InventoryItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件数据存储类
 * 负责将库存数据保存到文件，并从文件加载数据
 * 使用对象序列化方式实现数据持久化
 */
public class FileDataStore {
    
    private static final String DATA_FILE = "data/inventory_data.txt";
    
    /**
     * 保存数据到文件
     */
    public void saveData(List<InventoryItem> inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_FILE))) {
            oos.writeObject(inventory);
            System.out.println("数据已保存到: " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("数据保存失败: " + e.getMessage());
        }
    }
    
    /**
     * 从文件加载数据
     * @return 加载的库存数据，如果文件不存在或加载失败返回null
     */
    @SuppressWarnings("unchecked")
    public List<InventoryItem> loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("数据文件不存在，将使用初始测试数据。");
            return null;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DATA_FILE))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                return (List<InventoryItem>) obj;
            }
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("数据加载失败: " + e.getMessage());
            return null;
        }
    }
}