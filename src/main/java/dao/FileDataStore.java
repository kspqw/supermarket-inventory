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

    private static final String DATA_DIR = "data";
    private static final String DATA_FILE = DATA_DIR + File.separator + "inventory_data.txt";

    /**
     * 确保数据目录存在
     */
    private void ensureDataDirectoryExists() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                System.out.println("已创建数据目录: " + DATA_DIR);
            } else {
                System.err.println("无法创建数据目录: " + DATA_DIR);
            }
        }
    }

    /**
     * 保存数据到文件
     */
    public void saveData(List<InventoryItem> inventory) {
        ensureDataDirectoryExists();  // 确保目录存在

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_FILE))) {
            oos.writeObject(inventory);
            System.out.println("数据已保存到: " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("数据保存失败: " + e.getMessage());
        }
    }

    ？
    }
}