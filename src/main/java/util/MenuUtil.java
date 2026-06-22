package util;

/**
 * 菜单工具类
 * 提供主菜单和子菜单的显示
 */
public class MenuUtil {
    
    /**
     * 显示主菜单
     */
    public static void showMainMenu() {
        System.out.println("\n========================================");
        System.out.println("          超市库存管理系统");
        System.out.println("========================================");
        System.out.println("  1. 添加商品");
        System.out.println("  2. 删除商品");
        System.out.println("  3. 修改商品");
        System.out.println("  4. 查询商品");
        System.out.println("  5. 显示所有商品");
        System.out.println("  6. 库存统计");
        System.out.println("  7. 排序");
        System.out.println("  0. 退出系统");
        System.out.println("========================================");
    }
}