package vn.iotstar.service;

import java.util.List;
import vn.iotstar.dao.CategoryDao;
import vn.iotstar.entity.Category;

public class CategoryService {
    private final CategoryDao dao = new CategoryDao();

    public List<Category> findAll() { return dao.findAll(); }
    public Category findById(int id) { return dao.findById(id); }
    public List<Category> searchByName(String k) { return dao.searchByName(k); }
    public Category findByName(String name) { return dao.findByName(name); }

    /** @return false nếu tên đã tồn tại */
    public boolean insert(Category c) {
        if (dao.findByName(c.getCategoryname()) != null) return false;
        dao.insert(c);
        return true;
    }

    public void update(Category c) {
        if (dao.findById(c.getCategoryId()) != null) dao.update(c);
    }

    public void delete(int id) { dao.delete(id); }
}
