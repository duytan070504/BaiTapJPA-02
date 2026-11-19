package vn.iotstar.util;

import jakarta.servlet.http.Part;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FormValidator {
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE = Pattern.compile("^(0|\\+84)[0-9]{9}$");
    private static final Pattern USERNAME = Pattern.compile("^[A-Za-z0-9_.]{4,30}$");
    private static final Pattern OTP = Pattern.compile("^[0-9]{6}$");

    private static boolean blank(String s) { return s == null || s.isBlank(); }

    public static Map<String, String> login(String username, String password) {
        Map<String, String> e = new LinkedHashMap<>();
        if (blank(username)) e.put("username", "Vui lòng nhập tên đăng nhập");
        if (blank(password)) e.put("password", "Vui lòng nhập mật khẩu");
        return e;
    }

    public static Map<String, String> register(String username, String fullname, String email,
            String phone, String password, String confirm) {
        Map<String, String> e = new LinkedHashMap<>();
        if (blank(username)) e.put("username", "Vui lòng nhập tên đăng nhập");
        else if (!USERNAME.matcher(username.trim()).matches())
            e.put("username", "Tên đăng nhập 4-30 ký tự, chỉ gồm chữ, số, dấu _ và dấu .");

        if (blank(fullname)) e.put("fullname", "Vui lòng nhập họ tên");
        else if (fullname.trim().length() > 100) e.put("fullname", "Họ tên tối đa 100 ký tự");

        if (blank(email)) e.put("email", "Vui lòng nhập email");
        else if (!EMAIL.matcher(email.trim()).matches()) e.put("email", "Email không hợp lệ");

        if (blank(phone)) e.put("phone", "Vui lòng nhập số điện thoại");
        else if (!PHONE.matcher(phone.trim()).matches())
            e.put("phone", "Số điện thoại không hợp lệ (10 số, bắt đầu bằng 0 hoặc +84)");

        passwordRules(e, password, confirm);
        return e;
    }

    public static Map<String, String> email(String email) {
        Map<String, String> e = new LinkedHashMap<>();
        if (blank(email)) e.put("email", "Vui lòng nhập email");
        else if (!EMAIL.matcher(email.trim()).matches()) e.put("email", "Email không hợp lệ");
        return e;
    }

    public static Map<String, String> otp(String otp) {
        Map<String, String> e = new LinkedHashMap<>();
        otpRule(e, otp);
        return e;
    }

    public static Map<String, String> reset(String otp, String password, String confirm) {
        Map<String, String> e = new LinkedHashMap<>();
        otpRule(e, otp);
        passwordRules(e, password, confirm);
        return e;
    }

    public static Map<String, String> category(String name, String status, String link, Part image) {
        Map<String, String> e = new LinkedHashMap<>();
        if (blank(name)) e.put("categoryname", "Không được phép rỗng");
        else if (name.trim().length() > 255) e.put("categoryname", "Tên tối đa 255 ký tự");

        if (!"0".equals(status) && !"1".equals(status)) e.put("status", "Vui lòng chọn trạng thái");

        if (!blank(link) && !link.trim().matches("^https?://\\S+$"))
            e.put("images", "Link ảnh phải bắt đầu bằng http:// hoặc https://");
        if (!typeOk(image, "image/")) e.put("images1", "File tải lên phải là ảnh");
        return e;
    }

    public static Map<String, String> video(String title, String desc, Part poster, Part video) {
        Map<String, String> e = new LinkedHashMap<>();
        if (blank(title)) e.put("title", "Tiêu đề không được để trống");
        else if (title.trim().length() > 255) e.put("title", "Tiêu đề tối đa 255 ký tự");
        if (desc != null && desc.length() > 4000) e.put("description", "Mô tả tối đa 4000 ký tự");
        if (!typeOk(poster, "image/")) e.put("posterFile", "Poster phải là file ảnh");
        if (!typeOk(video, "video/")) e.put("videoFileUpload", "File phải là video (mp4, webm...)");
        return e;
    }

    // ---- helpers
    private static void passwordRules(Map<String, String> e, String password, String confirm) {
        if (blank(password)) e.put("password", "Vui lòng nhập mật khẩu");
        else if (password.length() < 6) e.put("password", "Mật khẩu tối thiểu 6 ký tự");
        if (blank(confirm)) e.put("confirm", "Vui lòng nhập lại mật khẩu");
        else if (!confirm.equals(password)) e.put("confirm", "Mật khẩu nhập lại không khớp");
    }

    private static void otpRule(Map<String, String> e, String otp) {
        if (blank(otp)) e.put("otp", "Vui lòng nhập mã OTP");
        else if (!OTP.matcher(otp.trim()).matches()) e.put("otp", "Mã OTP gồm đúng 6 chữ số");
    }

    /** Không chọn file thì hợp lệ; có chọn thì phải đúng loại */
    private static boolean typeOk(Part p, String prefix) {
        if (p == null || p.getSize() <= 0) return true;
        String t = p.getContentType();
        return t != null && t.toLowerCase().startsWith(prefix);
    }
}
