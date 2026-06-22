import model.InventoryItem;
import model.Product;
import service.InventoryService;
import service.InventoryServiceImpl;
import util.InputValidator;
import util.MenuUtil;

import java.util.List;
import java.util.Scanner;

/**
 * 超市库存管理系统 - 主程序入口
 * 采用控制台交互方式，实现商品库存的增加、删除、修改、查询、排序和统计功能
 */
public class Main {
    private static InventoryService inventoryService = new InventoryServiceImpl();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("     欢迎使用超市库存管理系统 v1.0");
        System.out.println("========================================");
        
        // 加载数据
        inventoryService.loadData();
        
        boolean running = true;
        while (running) {
            MenuUtil.showMainMenu();
            int choice = InputValidator.getIntInput(scanner, "请选择操作: ", 0, 7);
            
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    deleteProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    queryProduct();
                    break;
                case 5:
                    listAllProducts();
                    break;
                case 6:
                    statistics();
                    break;
                case 7:
                    sortProducts();
                    break;
                case 0:
                    running = false;
                    // 保存数据
                    inventoryService.saveData();
                    System.out.println("数据已保存，感谢使用！再见！");
                    break;
                default:
                    System.out.println("无效选项，请重新选择！");
            }
        }
        scanner.close();
    }

    /**
     * 添加商品
     */
    private static void addProduct() {
        System.out.println("\n--- 添加商品 ---");
        
        System.out.print("请输入商品名称: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("商品名称不能为空！");
            return;
        }
        
        System.out.print("请输入商品分类: ");
        String category = scanner.nextLine().trim();
        if (category.isEmpty()) {
            System.out.println("商品分类不能为空！");
            return;
        }
        
        System.out.print("请输入商品单价: ");
        double price = InputValidator.getDoubleInput(scanner, "请输入有效的价格: ");
        if (price < 0) {
            System.out.println("价格不能为负数！");
            return;
        }
        
        System.out.print("请输入库存数量: ");
        int quantity = InputValidator.getIntInput(scanner, "请输入有效的数量: ", 0, Integer.MAX_VALUE);
        
        Product product = new Product(name, category, price);
        InventoryItem item = new InventoryItem(product, quantity);
        
        if (inventoryService.addProduct(item)) {
            System.out.println("✅ 商品添加成功！");
            System.out.println("商品ID: " + item.getProduct().getId());
        } else {
            System.out.println("❌ 商品添加失败，该商品可能已存在！");
        }
    }

    /**
     * 删除商品
     */
    private static void deleteProduct() {
        System.out.println("\n--- 删除商品 ---");
        String id = InputValidator.getProductIdInput(scanner, "请输入要删除的商品ID: ");
        
        InventoryItem item = inventoryService.findProductById(id);
        if (item == null) {
            System.out.println("❌ 未找到该商品！");
            return;
        }
        
        System.out.println("要删除的商品信息: ");
        System.out.println(item);
        System.out.print("确认删除？(y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if ("y".equals(confirm) || "yes".equals(confirm)) {
            if (inventoryService.deleteProduct(id)) {
                System.out.println("✅ 商品删除成功！");
            } else {
                System.out.println("❌ 商品删除失败！");
            }
        } else {
            System.out.println("已取消删除操作。");
        }
    }

    /**
     * 修改商品
     */
    private static void updateProduct() {
        System.out.println("\n--- 修改商品 ---");
        String id = InputValidator.getProductIdInput(scanner, "请输入要修改的商品ID: ");
        
        InventoryItem existingItem = inventoryService.findProductById(id);
        if (existingItem == null) {
            System.out.println("❌ 未找到该商品！");
            return;
        }
        
        System.out.println("当前商品信息: ");
        System.out.println(existingItem);
        
        System.out.println("\n请选择要修改的字段:");
        System.out.println("1. 商品名称");
        System.out.println("2. 商品分类");
        System.out.println("3. 商品单价");
        System.out.println("4. 库存数量");
        System.out.println("5. 全部修改");
        System.out.println("0. 取消");
        
        int choice = InputValidator.getIntInput(scanner, "请选择: ", 0, 5);
        
        switch (choice) {
            case 1:
                System.out.print("请输入新的商品名称: ");
                String name = scanner.nextLine().trim();
                if (!name.isEmpty()) {
                    existingItem.getProduct().setName(name);
                }
                break;
            case 2:
                System.out.print("请输入新的商品分类: ");
                String category = scanner.nextLine().trim();
                if (!category.isEmpty()) {
                    existingItem.getProduct().setCategory(category);
                }
                break;
            case 3:
                double price = InputValidator.getDoubleInput(scanner, "请输入新的单价: ");
                if (price >= 0) {
                    existingItem.getProduct().setPrice(price);
                }
                break;
            case 4:
                int quantity = InputValidator.getIntInput(scanner, "请输入新的库存数量: ", 0, Integer.MAX_VALUE);
                existingItem.setQuantity(quantity);
                break;
            case 5:
                System.out.print("请输入新的商品名称: ");
                String newName = scanner.nextLine().trim();
                if (!newName.isEmpty()) {
                    existingItem.getProduct().setName(newName);
                }
                System.out.print("请输入新的商品分类: ");
                String newCategory = scanner.nextLine().trim();
                if (!newCategory.isEmpty()) {
                    existingItem.getProduct().setCategory(newCategory);
                }
                double newPrice = InputValidator.getDoubleInput(scanner, "请输入新的单价: ");
                if (newPrice >= 0) {
                    existingItem.getProduct().setPrice(newPrice);
                }
                int newQuantity = InputValidator.getIntInput(scanner, "请输入新的库存数量: ", 0, Integer.MAX_VALUE);
                existingItem.setQuantity(newQuantity);
                break;
            case 0:
                System.out.println("已取消修改操作。");
                return;
            default:
                System.out.println("无效选择！");
                return;
        }
        
        if (inventoryService.updateProduct(existingItem)) {
            System.out.println("✅ 商品信息修改成功！");
            System.out.println("修改后的信息: ");
            System.out.println(existingItem);
        } else {
            System.out.println("❌ 商品信息修改失败！");
        }
    }

    /**
     * 查询商品
     */
    private static void queryProduct() {
        System.out.println("\n--- 查询商品 ---");
        System.out.println("1. 按ID查询");
        System.out.println("2. 按名称查询");
        System.out.println("3. 按分类查询");
        System.out.println("0. 返回");
        
        int choice = InputValidator.getIntInput(scanner, "请选择: ", 0, 3);
        
        switch (choice) {
            case 1:
                String id = InputValidator.getProductIdInput(scanner, "请输入商品ID: ");
                InventoryItem item = inventoryService.findProductById(id);
                if (item != null) {
                    System.out.println("\n查询结果: ");
                    System.out.println(item);
                } else {
                    System.out.println("❌ 未找到该商品！");
                }
                break;
            case 2:
                System.out.print("请输入商品名称: ");
                String name = scanner.nextLine().trim();
                List<InventoryItem> items = inventoryService.findProductsByName(name);
                if (items.isEmpty()) {
                    System.out.println("❌ 未找到相关商品！");
                } else {
                    System.out.println("\n查询结果（共" + items.size() + "条）: ");
                    items.forEach(System.out::println);
                }
                break;
            case 3:
                System.out.print("请输入商品分类: ");
                String category = scanner.nextLine().trim();
                List<InventoryItem> categoryItems = inventoryService.findProductsByCategory(category);
                if (categoryItems.isEmpty()) {
                    System.out.println("❌ 未找到该分类的商品！");
                } else {
                    System.out.println("\n查询结果（共" + categoryItems.size() + "条）: ");
                    categoryItems.forEach(System.out::println);
                }
                break;
            case 0:
                break;
            default:
                System.out.println("无效选择！");
        }
    }

    /**
     * 列出所有商品
     */
    private static void listAllProducts() {
        System.out.println("\n--- 所有商品列表 ---");
        List<InventoryItem> items = inventoryService.getAllProducts();
        if (items.isEmpty()) {
            System.out.println("库存为空！");
            return;
        }
        
        System.out.println("库存总数: " + items.size() + " 种商品");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-12s %-15s %-10s %-10s %-10s%n", 
                "商品ID", "名称", "分类", "单价", "数量");
        System.out.println("------------------------------------------------------------");
        items.forEach(item -> 
            System.out.printf("%-12s %-15s %-10s %-10.2f %-10d%n",
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getProduct().getCategory(),
                    item.getProduct().getPrice(),
                    item.getQuantity())
        );
        System.out.println("------------------------------------------------------------");
        
        // 计算总库存价值
        double totalValue = items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        System.out.printf("库存总价值: %.2f 元%n", totalValue);
    }

    /**
     * 统计信息
     */
    private static void statistics() {
        System.out.println("\n--- 库存统计信息 ---");
        
        List<InventoryItem> items = inventoryService.getAllProducts();
        if (items.isEmpty()) {
            System.out.println("库存为空，无统计数据！");
            return;
        }
        
        // 商品总数
        System.out.println("商品种类数: " + items.size());
        
        // 库存总数量
        int totalQuantity = items.stream().mapToInt(InventoryItem::getQuantity).sum();
        System.out.println("库存总数量: " + totalQuantity);
        
        // 库存总价值
        double totalValue = items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        System.out.printf("库存总价值: %.2f 元%n", totalValue);
        
        // 最高单价商品
        InventoryItem maxPriceItem = items.stream()
                .max((a, b) -> Double.compare(a.getProduct().getPrice(), b.getProduct().getPrice()))
                .orElse(null);
        if (maxPriceItem != null) {
            System.out.println("最高单价商品: " + maxPriceItem.getProduct().getName() + 
                    " (" + maxPriceItem.getProduct().getPrice() + " 元)");
        }
        
        // 库存最多商品
        InventoryItem maxQuantityItem = items.stream()
                .max((a, b) -> Integer.compare(a.getQuantity(), b.getQuantity()))
                .orElse(null);
        if (maxQuantityItem != null) {
            System.out.println("库存最多商品: " + maxQuantityItem.getProduct().getName() + 
                    " (" + maxQuantityItem.getQuantity() + " 件)");
        }
        
        // 按分类统计
        System.out.println("\n按分类统计:");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-15s %-10s %-10s %-12s%n", "分类", "种类数", "总数量", "总价值");
        System.out.println("------------------------------------------------------------");
        items.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        item -> item.getProduct().getCategory(),
                        java.util.stream.Collectors.collectingAndThen(
                                java.util.stream.Collectors.toList(),
                                list -> {
                                    int count = list.size();
                                    int qty = list.stream().mapToInt(InventoryItem::getQuantity).sum();
                                    double val = list.stream()
                                            .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                                            .sum();
                                    return new Object[]{count, qty, val};
                                }
                        )
                ))
                .forEach((category, data) -> 
                    System.out.printf("%-15s %-10d %-10d %-12.2f%n",
                            category, data[0], data[1], data[2])
                );
        System.out.println("------------------------------------------------------------");
    }

    /**
     * 排序功能
     */
    private static void sortProducts() {
        System.out.println("\n--- 排序 ---");
        System.out.println("1. 按商品ID排序");
        System.out.println("2. 按商品名称排序");
        System.out.println("3. 按商品单价排序（升序）");
        System.out.println("4. 按商品单价排序（降序）");
        System.out.println("5. 按库存数量排序（升序）");
        System.out.println("6. 按库存数量排序（降序）");
        System.out.println("0. 返回");
        
        int choice = InputValidator.getIntInput(scanner, "请选择: ", 0, 6);
        if (choice == 0) return;
        
        List<InventoryItem> sortedList = inventoryService.sortProducts(choice);
        if (sortedList.isEmpty()) {
            System.out.println("库存为空！");
            return;
        }
        
        System.out.println("\n排序结果:");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-12s %-15s %-10s %-10s %-10s%n", 
                "商品ID", "名称", "分类", "单价", "数量");
        System.out.println("------------------------------------------------------------");
        sortedList.forEach(item -> 
            System.out.printf("%-12s %-15s %-10s %-10.2f %-10d%n",
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getProduct().getCategory(),
                    item.getProduct().getPrice(),
                    item.getQuantity())
        );
        System.out.println("------------------------------------------------------------");
    }
}