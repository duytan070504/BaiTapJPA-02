package vn.iotstar.dao;

import java.util.List;
import vn.iotstar.config.JpaHelper;
import vn.iotstar.entity.Category;

public class CategoryDao {
    public List<Category> findAll() {
        return JpaHelper.read(em -> em.createNamedQuery("Category.findAll", Category.class).getResultList());
    }

    public Category findById(int id) {
        return JpaHelper.read(em -> em.find(Category.class, id));
    }

    public List<Category> searchByName(String keyword) {
        return JpaHelper.read(em -> em
                .createQuery("SELECT c FROM Category c WHERE c.categoryname LIKE :k", Category.class)
                .setParameter("k", "%" + keyword + "%")
                .getResultList());
    }

    public Category findByName(String name) {
        return JpaHelper.read(em -> em
                .createQuery("SELECT c FROM Category c WHERE c.categoryname = :n", Category.class)
                .setParameter("n", name)
                .getResultStream().findFirst().orElse(null));
    }

    public void insert(Category c) {
        JpaHelper.tx(em -> em.persist(c));
    }

    public void update(Category c) {
        JpaHelper.tx(em -> em.merge(c));
    }

    public void delete(int id) {
        JpaHelper.tx(em -> {
            Category c = em.find(Category.class, id);
            if (c == null) throw new IllegalArgumentException("Không tìm thấy category");
            // gỡ liên kết các video đang thuộc category này trước khi xóa
            em.createQuery("UPDATE Video v SET v.category = null WHERE v.category.categoryId = :id")
              .setParameter("id", id).executeUpdate();
            em.remove(c);
        });
    }
}
