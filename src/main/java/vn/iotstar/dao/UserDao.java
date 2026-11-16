package vn.iotstar.dao;

import vn.iotstar.config.JpaHelper;
import vn.iotstar.entity.User;

public class UserDao {
    public User findByUsername(String username) {
        return JpaHelper.read(em -> em
                .createQuery("SELECT u FROM User u WHERE u.username = :n", User.class)
                .setParameter("n", username)
                .getResultStream().findFirst().orElse(null));
    }

    public User findByEmail(String email) {
        return JpaHelper.read(em -> em
                .createQuery("SELECT u FROM User u WHERE u.email = :e", User.class)
                .setParameter("e", email)
                .getResultStream().findFirst().orElse(null));
    }

    public User findByPhone(String phone) {
        return JpaHelper.read(em -> em
                .createQuery("SELECT u FROM User u WHERE u.phone = :p", User.class)
                .setParameter("p", phone)
                .getResultStream().findFirst().orElse(null));
    }

    /** id == 0 thì thêm mới, ngược lại cập nhật */
    public void save(User u) {
        JpaHelper.tx(em -> {
            if (u.getId() == 0) em.persist(u);
            else em.merge(u);
        });
    }
}
