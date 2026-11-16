<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="_header.jsp" %>
<div class="box narrow">
 <h2>Đặt lại mật khẩu</h2>
 <p>Mã OTP đã gửi tới <b><c:out value="${email}"/></b>.</p>
 <c:if test="${not empty error}"><div class="err"><c:out value="${error}"/></div></c:if>
 <form action="<c:url value='/reset-password'/>" method="post">
  <input type="hidden" name="email" value="<c:out value='${email}'/>">
  <label>Mã OTP</label><input type="text" name="otp" maxlength="6" required>
  <label>Mật khẩu mới</label><input type="password" name="password" required>
  <button type="submit">Đổi mật khẩu</button>
 </form>
</div>
<%@ include file="_footer.jsp" %>
