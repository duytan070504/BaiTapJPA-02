package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import vn.iotstar.entity.Category;
import vn.iotstar.service.CategoryService;
import vn.iotstar.util.FileUtil;
import vn.iotstar.util.WebUtil;

@MultipartConfig
@WebServlet(urlPatterns = { "/admin/categories", "/admin/category/add", "/admin/category/insert",
        "/admin/category/edit", "/admin/category/update", "/admin/category/delete" })
public class CategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryService cs = new CategoryService();

    private void view(HttpServletRequest req, HttpServletResponse resp, String name)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/" + name + ".jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String ctx = req.getContextPath();
        switch (path) {
            case "/admin/categories" -> {
                String k = req.getParameter("keyword");
                List<Category> list = (k == null || k.isBlank()) ? cs.findAll() : cs.searchByName(k.trim());
                req.setAttribute("listcate", list);
                view(req, resp, "category-list");
            }
            case "/admin/category/add" -> view(req, resp, "category-add");
            case "/admin/category/edit" -> {
                Category c = cs.findById(WebUtil.parseInt(req.getParameter("id"), 0));
                if (c == null) { resp.sendRedirect(ctx + "/admin/categories"); return; }
                req.setAttribute("cate", c);
                view(req, resp, "category-edit");
            }
            case "/admin/category/delete" -> {
                try {
                    Category c = cs.findById(WebUtil.parseInt(req.getParameter("id"), 0));
                    cs.delete(WebUtil.parseInt(req.getParameter("id"), 0));
                    if (c != null) FileUtil.delete(c.getImages());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                resp.sendRedirect(ctx + "/admin/categories");
            }
            default -> resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String ctx = req.getContextPath();
        String name = req.getParameter("categoryname");
        int status = WebUtil.parseInt(req.getParameter("status"), 1);
        String link = req.getParameter("images");
        String uploaded = FileUtil.save(req.getPart("images1"));

        if (path.equals("/admin/category/insert")) {
            Category c = new Category();
            c.setCategoryname(name);
            c.setStatus(status);
            if (uploaded != null) c.setImages(uploaded);
            else if (link != null && !link.isBlank()) c.setImages(link.trim());
            else c.setImages("avatar.png");

            if (name == null || name.isBlank() || !cs.insert(c)) {
                req.setAttribute("error", "Tên category trống hoặc đã tồn tại");
                view(req, resp, "category-add");
                return;
            }
            resp.sendRedirect(ctx + "/admin/categories");
        } else if (path.equals("/admin/category/update")) {
            Category c = cs.findById(WebUtil.parseInt(req.getParameter("categoryId"), 0));
            if (c == null) { resp.sendRedirect(ctx + "/admin/categories"); return; }
            String old = c.getImages();
            c.setCategoryname(name);
            c.setStatus(status);
            if (uploaded != null) {
                FileUtil.delete(old);          // xóa file cũ (nếu là file upload)
                c.setImages(uploaded);
            } else if (link != null && !link.isBlank()) {
                c.setImages(link.trim());
            }
            cs.update(c);
            resp.sendRedirect(ctx + "/admin/categories");
        } else {
            resp.sendError(404);
        }
    }
}
