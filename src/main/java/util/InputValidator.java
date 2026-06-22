package util;

import java.util.Scanner;

/**
 * 输入验证工具类
 * 提供各种输入验证和获取方法，包含异常处理
 */
public class InputValidator {
    
    /**
     * 获取整数输入，包含异常处理
     */
    public static int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("输入不能为空，请重新输入！");
                    continue;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("请输入有效的整数！");
            }
        }
    }
    
    /**
     * 获取指定范围的整数输入
     */
    public static int getIntInput(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            int value = getIntInput(scanner, prompt);
            if (value < min || value > max) {
                System.out.println("输入值必须在 " + min + " 到 " + max + " 之间，请重新输入！");
                continue;
            }
            return value;
        }
    }
    
    /**
     * 获取双精度浮点数输入
     */
    public static double getDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("输入不能为空，请重新输入！");
                    continue;
                }
                double value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println("请输入非负数！");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("请输入有效的数字！");
            }
        }
    }
    
    /**
     * 获取商品ID输入（非空）
     */
    public static String getProductIdInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("商品ID不能为空，请重新输入！");
                continue;
            }
            return input;
        }
    }
}