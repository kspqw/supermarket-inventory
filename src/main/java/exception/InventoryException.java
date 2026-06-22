package exception;

/**
 * 库存管理自定义异常类
 * 用于处理库存管理中的特定异常情况
 */
public class InventoryException extends Exception {
    
    public InventoryException() {
        super();
    }
    
    public InventoryException(String message) {
        super(message);
    }
    
    public InventoryException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InventoryException(Throwable cause) {
        super(cause);
    }
}