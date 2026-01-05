package util;

/**
 * Lớp tiện ích kiểm tra validation
 */
public class ValidationUtil {
    
    /**
     * Kiểm tra email hợp lệ
     * @param email Email cần kiểm tra
     * @return true nếu hợp lệ
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Regex đơn giản cho email
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    /**
     * Kiểm tra số điện thoại hợp lệ (10-11 số)
     * @param phone Số điện thoại cần kiểm tra
     * @return true nếu hợp lệ
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        // Loại bỏ khoảng trắng và dấu gạch ngang
        String cleanPhone = phone.replaceAll("[\\s-]", "");
        // Kiểm tra có phải 10-11 số không
        return cleanPhone.matches("^[0-9]{10,11}$");
    }
    
    /**
     * Kiểm tra password đủ mạnh (ít nhất 6 ký tự)
     * @param password Password cần kiểm tra
     * @return true nếu hợp lệ
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
    
    /**
     * Kiểm tra chuỗi null hoặc rỗng
     * @param str Chuỗi cần kiểm tra
     * @return true nếu null hoặc rỗng
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
