package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Lớp tạo mã booking tự động
 */
public class BookingCodeGenerator {
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random random = new Random();
    
    /**
     * Tạo mã booking duy nhất
     * Format: BK + YYMMDD + 4 ký tự ngẫu nhiên
     * Ví dụ: BK241221ABCD
     * 
     * @return Mã booking
     */
    public static String generateBookingCode() {
        // Lấy ngày hiện tại dạng YYMMDD
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String dateStr = sdf.format(new Date());
        
        // Tạo 4 ký tự ngẫu nhiên
        String randomPart = generateRandomString(4);
        
        return "BK" + dateStr + randomPart;
    }
    
    /**
     * Tạo chuỗi ngẫu nhiên
     * @param length Độ dài chuỗi
     * @return Chuỗi ngẫu nhiên
     */
    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}