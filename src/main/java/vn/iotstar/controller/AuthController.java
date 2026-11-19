package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import vn.iotstar.entity.User;
import vn.iotstar.service.UserService;
import vn.iotstar.util.FormValidator;
import vn.iotstar.util.PasswordUtil;

@WebServlet({ "/login", "/logout", "/register", "/verify-otp", "/resend-otp",
              "/forgot-password", "/reset-password" })
public class AuthController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService us = new UserService();

    private void view(HttpServletRequest req, HttpServletResponse resp, String name)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/" + name + ".jsp").forward(req, resp);
    }

    private void invalid(HttpServletRequest req, HttpServletResponse resp, String page,
            Map<String, String> errors) throws ServletException, IOException {
        req.setAttribute("errors", errors);
        view(req, resp, page);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/login" -> view(req, resp, "login");
            case "/register" -> view(req, resp, "register");
            case "/forgot-password" -> view(req, resp, "forgot-password");
            case "/verify-otp" -> {
                req.setAttribute("email", req.getParameter("email"));
                view(req, resp, "verify-otp");
            }
            case "/reset-password" -> {
                req.setAttribute("email", req.getParameter("email"));
                view(req, resp, "reset-password");
            }
            case "/logout" -> {
                HttpSession s = req.getSession(false);
                if (s != null) s.invalidate();
                resp.sendRedirect(req.getContextPath() + "/login");
            }
            case "/resend-otp" -> {
                String email = req.getParameter("email");
                String err = us.resendActivationOtp(email);
                req.setAttribute("email", email);
                if (err != null) req.setAttribute("error", err);
                else req.setAttribute("msg", "Đã gửi lại mã OTP, vui lòng kiểm tra email");
                view(req, resp, "verify-otp");
            }
            default -> resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String ctx = req.getContextPath();
        switch (path) {
            case "/login" -> {
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                Map<String, String> errors = FormValidator.login(username, password);
                if (!errors.isEmpty()) { invalid(req, resp, "login", errors); return; }

                User u = us.findByUsername(username.trim());
                if (u == null || !PasswordUtil.check(password, u.getPassword())) {
                    req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
                    view(req, resp, "login");
                } else if (!u.isActive()) {
                    req.setAttribute("error", "Tài khoản chưa được kích hoạt. Hãy nhập mã OTP đã gửi qua email.");
                    req.setAttribute("verifyEmail", u.getEmail());
                    view(req, resp, "login");
                } else {
                    req.getSession(true);
                    req.changeSessionId();
                    req.getSession().setAttribute("user", u);
                    resp.sendRedirect(ctx + "/home");
                }
            }
            case "/register" -> {
                String email = req.getParameter("email");
                Map<String, String> errors = FormValidator.register(req.getParameter("username"),
                        req.getParameter("fullname"), email, req.getParameter("phone"),
                        req.getParameter("password"), req.getParameter("confirm"));
                if (!errors.isEmpty()) { invalid(req, resp, "register", errors); return; }

                String err = us.register(req.getParameter("username"), email,
                        req.getParameter("password"), req.getParameter("fullname"), req.getParameter("phone"));
                if (err != null) {
                    req.setAttribute("error", err);
                    view(req, resp, "register");
                } else {
                    resp.sendRedirect(ctx + "/verify-otp?email="
                            + URLEncoder.encode(email.trim().toLowerCase(), StandardCharsets.UTF_8));
                }
            }
            case "/verify-otp" -> {
                String email = req.getParameter("email");
                req.setAttribute("email", email);
                Map<String, String> errors = FormValidator.otp(req.getParameter("otp"));
                if (!errors.isEmpty()) { invalid(req, resp, "verify-otp", errors); return; }

                String err = us.verifyActivation(email, req.getParameter("otp"));
                if (err != null) {
                    req.setAttribute("error", err);
                    view(req, resp, "verify-otp");
                } else {
                    req.setAttribute("msg", "Kích hoạt tài khoản thành công, hãy đăng nhập");
                    view(req, resp, "login");
                }
            }
            case "/forgot-password" -> {
                String email = req.getParameter("email");
                Map<String, String> errors = FormValidator.email(email);
                if (!errors.isEmpty()) { invalid(req, resp, "forgot-password", errors); return; }

                String err = us.startReset(email);
                if (err != null) {
                    req.setAttribute("error", err);
                    view(req, resp, "forgot-password");
                } else {
                    resp.sendRedirect(ctx + "/reset-password?email="
                            + URLEncoder.encode(email.trim().toLowerCase(), StandardCharsets.UTF_8));
                }
            }
            case "/reset-password" -> {
                String email = req.getParameter("email");
                req.setAttribute("email", email);
                Map<String, String> errors = FormValidator.reset(req.getParameter("otp"),
                        req.getParameter("password"), req.getParameter("confirm"));
                if (!errors.isEmpty()) { invalid(req, resp, "reset-password", errors); return; }

                String err = us.resetPassword(email, req.getParameter("otp"), req.getParameter("password"));
                if (err != null) {
                    req.setAttribute("error", err);
                    view(req, resp, "reset-password");
                } else {
                    req.setAttribute("msg", "Đổi mật khẩu thành công, hãy đăng nhập");
                    view(req, resp, "login");
                }
            }
            default -> resp.sendError(404);
        }
    }
}
