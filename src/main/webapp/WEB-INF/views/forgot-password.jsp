<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="_header.jsp" %>
<div class="box narrow">
 <h2>Quên mật khẩu</h2>
 <c:if test="${not empty error}"><div class="err"><c:out value="${error}"/></div></c:if>
 <form action="<c:url value='/forgot-password'/>" method="post">
  <label>Email đã đăng ký</label><input type="email" name="email" required>
  <button type="submit">Gửi mã OTP</button>
 </form>
 <p><a href="<c:url value='/login'/>">Quay lại đăng nhập</a></p>
</div>
<%@ include file="_footer.jsp" %>
