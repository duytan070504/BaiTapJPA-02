package vn.iotstar.service;

import java.time.LocalDateTime;
import vn.iotstar.dao.UserDao;
import vn.iotstar.entity.User;
import vn.iotstar.util.MailUtil;
import vn.iotstar.util.OtpUtil;
import vn.iotstar.util.PasswordUtil;

/** Các hàm trả về String: null = thành công, khác null = thông báo lỗi. */
public class UserService {
    private static final int OTP_MINUTES = 5;
    private final UserDao dao = new UserDao();

    public User findByUsername(String username) { return dao.findByUsername(username); }
    public User findByEmail(String email) { return dao.findByEmail(email); }

    // ===== Đăng ký + gửi OTP kích hoạt =====
    public String register(String username, String email, String password, String fullname, String phone) {
        if (blank(username) || blank(email) || blank(password) || blank(fullname) || blank(phone))
            return "Vui lòng nhập đầy đủ thông tin";
        if (password.length() < 6)
            return "Mật khẩu tối thiểu 6 ký tự";
        username = username.trim();
        email = email.trim().toLowerCase();

        User u = dao.findByUsername(username);
        if (u != null && u.isActive()) return "Tên đăng nhập đã tồn tại";
        User byEmail = dao.findByEmail(email);
        if (byEmail != null && (u == null || byEmail.getId() != u.getId()))
            return "Email đã được sử dụng";

        phone = phone.trim();
        User byPhone = dao.findByPhone(phone);
        if (byPhone != null && (u == null || byPhone.getId() != u.getId()))
            return "Số điện thoại đã tồn tại";

        // nếu tài khoản cũ chưa kích hoạt thì cho đăng ký lại (ghi đè thông tin)
        if (u == null) u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setFullname(fullname);
        u.setPhone(phone);
        if (u.getCreatedDate() == null) u.setCreatedDate(LocalDateTime.now());
        u.setPassword(PasswordUtil.hash(password));
        u.setActive(false);
        assignOtp(u);
        dao.save(u);

        return sendOtp(u, "Mã OTP kích hoạt tài khoản");
    }

    public String resendActivationOtp(String email) {
        User u = dao.findByEmail(email);
        if (u == null) return "Email không tồn tại";
        if (u.isActive()) return "Tài khoản đã được kích hoạt";
        assignOtp(u);
        dao.save(u);
        return sendOtp(u, "Mã OTP kích hoạt tài khoản");
    }

    public String verifyActivation(String email, String otp) {
        User u = dao.findByEmail(email);
        if (u == null) return "Email không tồn tại";
        String err = checkOtp(u, otp);
        if (err != null) return err;
        u.setActive(true);
        u.setOtp(null);
        u.setOtpExpiry(null);
        dao.save(u);
        return null;
    }

    // ===== Quên mật khẩu =====
    public String startReset(String email) {
        if (blank(email)) return "Vui lòng nhập email";
        User u = dao.findByEmail(email.trim().toLowerCase());
        if (u == null || !u.isActive()) return "Email không tồn tại hoặc tài khoản chưa kích hoạt";
        assignOtp(u);
        dao.save(u);
        return sendOtp(u, "Mã OTP đặt lại mật khẩu");
    }

    public String resetPassword(String email, String otp, String newPassword) {
        if (blank(newPassword) || newPassword.length() < 6) return "Mật khẩu mới tối thiểu 6 ký tự";
        User u = dao.findByEmail(email);
        if (u == null) return "Email không tồn tại";
        String err = checkOtp(u, otp);
        if (err != null) return err;
        u.setPassword(PasswordUtil.hash(newPassword));
        u.setOtp(null);
        u.setOtpExpiry(null);
        dao.save(u);
        return null;
    }

    // ===== helper =====
    private void assignOtp(User u) {
        u.setOtp(OtpUtil.generate());
        u.setOtpExpiry(LocalDateTime.now().plusMinutes(OTP_MINUTES));
    }

    private String checkOtp(User u, String otp) {
        if (u.getOtp() == null || otp == null || !u.getOtp().equals(otp.trim()))
            return "Mã OTP không đúng";
        if (u.getOtpExpiry() == null || u.getOtpExpiry().isBefore(LocalDateTime.now()))
            return "Mã OTP đã hết hạn, vui lòng gửi lại";
        return null;
    }

    private String sendOtp(User u, String subject) {
        try {
            MailUtil.send(u.getEmail(), subject,
                    "Xin chào " + (u.getFullname() == null ? u.getUsername() : u.getFullname())
                    + ",\n\nMã OTP của bạn là: " + u.getOtp()
                    + "\nMã có hiệu lực trong " + OTP_MINUTES + " phút.\n");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Không gửi được email OTP (kiểm tra mail.user / mail.password trong config.properties): "
                    + e.getMessage();
        }
    }

    private boolean blank(String s) { return s == null || s.isBlank(); }
}
