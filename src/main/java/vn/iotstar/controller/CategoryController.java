package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import vn.iotstar.entity.Category;
import vn.iotstar.service.CategoryService;
import vn.iotstar.util.FileUtil;
import vn.iotstar.util.FormValidator;
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
                    int id = WebUtil.parseInt(req.getParameter("id"), 0);
                    Category c = cs.findById(id);
                    cs.delete(id);
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
        String statusStr = req.getParameter("status");
        String link = req.getParameter("images");
        Part imgPart = req.getPart("images1");

        Map<String, String> errors = FormValidator.category(name, statusStr, link, imgPart);
        int status = WebUtil.parseInt(statusStr, 1);

        if (path.equals("/admin/category/insert")) {
            if (errors.isEmpty() && !cs.findAll().stream().noneMatch(c -> c.getCategoryname().equalsIgnoreCase(name.trim())))
                errors.put("categoryname", "Tên category đã tồn tại");
            if (!errors.isEmpty()) {
                req.setAttribute("errors", errors);
                view(req, resp, "category-add");
                return;
            }
            Category c = new Category();
            c.setCategoryname(name.trim());
            c.setStatus(status);
            String uploaded = FileUtil.save(imgPart);
            if (uploaded != null) c.setImages(uploaded);
            else if (link != null && !link.isBlank()) c.setImages(link.trim());
            else c.setImages("avatar.png");
            cs.insert(c);
            resp.sendRedirect(ctx + "/admin/categories");

        } else if (path.equals("/admin/category/update")) {
            Category c = cs.findById(WebUtil.parseInt(req.getParameter("categoryId"), 0));
            if (c == null) { resp.sendRedirect(ctx + "/admin/categories"); return; }
            if (errors.isEmpty()) {
                boolean dup = cs.findAll().stream().anyMatch(x ->
                        x.getCategoryId() != c.getCategoryId() && x.getCategoryname().equalsIgnoreCase(name.trim()));
                if (dup) errors.put("categoryname", "Tên category đã tồn tại");
            }
            if (!errors.isEmpty()) {
                // giữ lại dữ liệu người dùng vừa nhập để hiển thị (không lưu DB)
                if (name != null) c.setCategoryname(name);
                c.setStatus(status);
                req.setAttribute("errors", errors);
                req.setAttribute("cate", c);
                view(req, resp, "category-edit");
                return;
            }
            String old = c.getImages();
            c.setCategoryname(name.trim());
            c.setStatus(status);
            String uploaded = FileUtil.save(imgPart);
            if (uploaded != null) {
                FileUtil.delete(old);
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
