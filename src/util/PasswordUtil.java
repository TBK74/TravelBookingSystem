package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Lớp tiện ích xử lý mật khẩu
 * Sử dụng SHA-256 để mã hóa password
 */
public class PasswordUtil {
    
    /**
     * Mã hóa password bằng SHA-256
     * @param password Password cần mã hóa
     * @return Chuỗi đã mã hóa dạng hex
     */
    public static String hashPassword(String password) {
        try {
            // Tạo MessageDigest với thuật toán SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            // Hash password thành mảng byte
            byte[] hashedBytes = md.digest(password.getBytes());
            
            // Chuyển mảng byte thành chuỗi hex
            return bytesToHex(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Lỗi mã hóa password", e);
        }
    }
    
    /**
     * So sánh password người dùng nhập với password đã hash trong DB
     * @param inputPassword Password người dùng nhập
     * @param hashedPassword Password đã hash trong database
     * @return true nếu khớp, false nếu không
     */
    public static boolean verifyPassword(String inputPassword, String hashedPassword) {
        String hashOfInput = hashPassword(inputPassword);
        return hashOfInput.equals(hashedPassword);
    }
    
    /**
     * Chuyển mảng byte thành chuỗi hex
     * @param bytes Mảng byte cần chuyển
     * @return Chuỗi hex
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}