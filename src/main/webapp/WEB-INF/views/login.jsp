<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="_header.jsp" %>
<div class="box narrow">
 <h2>Đăng nhập</h2>
 <c:if test="${not empty error}"><div class="err"><c:out value="${error}"/></div></c:if>
 <c:if test="${not empty msg}"><div class="ok"><c:out value="${msg}"/></div></c:if>
 <c:if test="${not empty verifyEmail}">
  <p><a href="<c:url value='/resend-otp'><c:param name='email' value='${verifyEmail}'/></c:url>">Gửi lại OTP &amp; kích hoạt tài khoản</a></p>
 </c:if>
 <form action="<c:url value='/login'/>" method="post">
  <label>Tên đăng nhập</label><input type="text" name="username" required>
  <label>Mật khẩu</label><input type="password" name="password" required>
  <button type="submit">Đăng nhập</button>
 </form>
 <p><a href="<c:url value='/forgot-password'/>">Quên mật khẩu?</a> | <a href="<c:url value='/register'/>">Đăng ký</a></p>
</div>
<%@ include file="_footer.jsp" %>
