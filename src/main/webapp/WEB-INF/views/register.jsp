<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="_header.jsp" %>
<div class="box narrow">
 <h2>Đăng ký</h2>
 <c:if test="${not empty error}"><div class="err"><c:out value="${error}"/></div></c:if>
 <form action="<c:url value='/register'/>" method="post">
  <label>Tên đăng nhập</label><input type="text" name="username" value="<c:out value='${param.username}'/>" required>
  <label>Họ tên</label><input type="text" name="fullname" value="<c:out value='${param.fullname}'/>">
  <label>Email (nhận mã OTP)</label><input type="email" name="email" value="<c:out value='${param.email}'/>" required>
  <label>Số điện thoại</label><input type="text" name="phone" value="<c:out value='${param.phone}'/>" required>
  <label>Mật khẩu (tối thiểu 6 ký tự)</label><input type="password" name="password" required>
  <button type="submit">Đăng ký &amp; gửi OTP</button>
 </form>
 <p>Đã có tài khoản? <a href="<c:url value='/login'/>">Đăng nhập</a></p>
</div>
<%@ include file="_footer.jsp" %>
