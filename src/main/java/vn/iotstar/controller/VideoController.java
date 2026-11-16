package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;
import vn.iotstar.entity.Video;
import vn.iotstar.service.CategoryService;
import vn.iotstar.service.VideoService;
import vn.iotstar.util.FileUtil;
import vn.iotstar.util.WebUtil;

@MultipartConfig(maxFileSize = 1024L * 1024 * 500, maxRequestSize = 1024L * 1024 * 520)
@WebServlet({ "/video", "/video/add", "/video/insert", "/video/edit", "/video/update", "/video/delete" })
public class VideoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final VideoService vs = new VideoService();
    private final CategoryService cs = new CategoryService();

    private void view(HttpServletRequest req, HttpServletResponse resp, String name)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/" + name + ".jsp").forward(req, resp);
    }

    /** Lấy video theo id và chỉ cho phép chủ sở hữu thao tác */
    private Video owned(HttpServletRequest req, User u) {
        Video v = vs.findById(WebUtil.parseInt(req.getParameter("id"), 0));
        return (v != null && v.getUser().getId() == u.getId()) ? v : null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String ctx = req.getContextPath();
        User u = (User) req.getSession().getAttribute("user");

        switch (path) {
            case "/video" -> {   // tất cả video của user, 6 video / trang
                int totalPages = vs.totalPages(u.getId());
                int page = Math.min(Math.max(WebUtil.parseInt(req.getParameter("page"), 1), 1), totalPages);
                req.setAttribute("videos", vs.findByUser(u.getId(), page));
                req.setAttribute("page", page);
                req.setAttribute("totalPages", totalPages);
                view(req, resp, "video-list");
            }
            case "/video/add" -> {
                req.setAttribute("categories", cs.findAll());
                view(req, resp, "video-form");
            }
            case "/video/edit" -> {
                Video v = owned(req, u);
                if (v == null) { resp.sendRedirect(ctx + "/video"); return; }
                req.setAttribute("video", v);
                req.setAttribute("categories", cs.findAll());
                view(req, resp, "video-form");
            }
            case "/video/delete" -> {
                Video v = owned(req, u);
                if (v != null) {
                    vs.delete(v.getId());
                    FileUtil.delete(v.getPoster());
                    FileUtil.delete(v.getVideoFile());
                }
                resp.sendRedirect(ctx + "/video");
            }
            default -> resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String ctx = req.getContextPath();
        User u = (User) req.getSession().getAttribute("user");

        String title = req.getParameter("title");
        String desc = req.getParameter("description");
        boolean active = req.getParameter("active") != null;
        int catId = WebUtil.parseInt(req.getParameter("categoryId"), 0);
        Category cat = catId > 0 ? cs.findById(catId) : null;
        String poster = FileUtil.save(req.getPart("posterFile"));
        String videoFile = FileUtil.save(req.getPart("videoFileUpload"));

        if (title == null || title.isBlank()) {
            req.setAttribute("error", "Tiêu đề không được để trống");
            if (path.equals("/video/update")) req.setAttribute("video", owned(req, u));
            req.setAttribute("categories", cs.findAll());
            view(req, resp, "video-form");
            return;
        }

        if (path.equals("/video/insert")) {
            Video v = new Video();
            v.setTitle(title.trim());
            v.setDescription(desc);
            v.setActive(active);
            v.setCategory(cat);
            v.setPoster(poster);
            v.setVideoFile(videoFile);
            v.setUser(u);              // gắn video cho user đang đăng nhập (quan hệ 1-n)
            vs.insert(v);
        } else if (path.equals("/video/update")) {
            Video v = vs.findById(WebUtil.parseInt(req.getParameter("id"), 0));
            if (v == null || v.getUser().getId() != u.getId()) { resp.sendRedirect(ctx + "/video"); return; }
            v.setTitle(title.trim());
            v.setDescription(desc);
            v.setActive(active);
            v.setCategory(cat);
            if (poster != null) { FileUtil.delete(v.getPoster()); v.setPoster(poster); }
            if (videoFile != null) { FileUtil.delete(v.getVideoFile()); v.setVideoFile(videoFile); }
            vs.update(v);
        } else {
            resp.sendError(404);
            return;
        }
        resp.sendRedirect(ctx + "/video");
    }
}
