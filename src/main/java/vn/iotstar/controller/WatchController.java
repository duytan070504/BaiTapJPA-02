package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import vn.iotstar.entity.Video;
import vn.iotstar.service.VideoService;
import vn.iotstar.util.WebUtil;

/** Xem chi tiết + play 1 video (dùng chung cho /home và /video) */
@WebServlet("/watch")
public class WatchController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final VideoService vs = new VideoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = WebUtil.parseInt(req.getParameter("id"), 0);
        Video v = vs.findById(id);
        if (v == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        vs.increaseViews(id);
        v.setViews(v.getViews() + 1);
        req.setAttribute("video", v);
        req.getRequestDispatcher("/WEB-INF/views/watch.jsp").forward(req, resp);
    }
}
