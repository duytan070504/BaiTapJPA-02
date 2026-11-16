package vn.iotstar.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

/** Phải đăng nhập mới vào được /video/* và /admin/* */
@WebFilter({ "/video", "/video/*", "/admin/*" })
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) req;
        HttpSession s = r.getSession(false);
        if (s != null && s.getAttribute("user") != null) {
            chain.doFilter(req, resp);
        } else {
            ((HttpServletResponse) resp).sendRedirect(r.getContextPath() + "/login");
        }
    }
}
