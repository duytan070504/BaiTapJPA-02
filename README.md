# BaiTapJPA-02 (JPA/Hibernate + Servlet/JSP, Tomcat 11, SQL Server)

## Chạy thử
1. `persistence.xml` đã trỏ sẵn tới SQL Server của bài 1 (MAYYY, LoginDB). Hibernate chỉ tạo thêm bảng users, categories, videos.
2. Sửa `src/main/resources/config.properties`: upload.dir, mail.user, mail.password (Gmail App Password).
3. Spring Tools: File > Import > Maven > Existing Maven Projects. Chuột phải project > Maven > Update Project.
4. Chuột phải project > Run As > Run on Server (Tomcat 11). Mở http://localhost:8080/BaiTapJPA-02/
   (Hibernate tự tạo bảng users, categories, videos nhờ hbm2ddl.auto=update)

## Đường dẫn
/register /verify-otp /login /logout /forgot-password /reset-password
/home (10 video mới nhất)   /video (video của user, 6/trang)   /watch?id= (chi tiết + play)
/video/add|edit|delete      /admin/categories (CRUD category)

## Bài 03
- SiteMesh 3.2.1: `pom.xml`, `WEB-INF/web.xml` (filter), `WEB-INF/sitemesh3.xml`, `WEB-INF/decorators/{web,admin,login}.jsp`, `common/{web,admin}/...` (Bootstrap 5 qua CDN).
- Validation (server + HTML5) cho login, register, forgot/reset password, verify OTP, category (insert/edit), video (insert/edit): `util/FormValidator.java`.
