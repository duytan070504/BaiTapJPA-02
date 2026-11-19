<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html><head><title>Đăng nhập</title></head>
<body>
<h4 class="text-center mb-4">Đăng nhập</h4>
<c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
<c:if test="${not empty msg}"><div class="alert alert-success"><c:out value="${msg}"/></div></c:if>
<c:if test="${not empty verifyEmail}">
  <p><a href="<c:url value='/resend-otp'><c:param name='email' value='${verifyEmail}'/></c:url>">Gửi lại OTP &amp; kích hoạt tài khoản</a></p>
</c:if>
<form action="<c:url value='/login'/>" method="post">
  <div class="mb-3">
    <label class="form-label">Tên đăng nhập</label>
    <input type="text" name="username" required value="<c:out value='${param.username}'/>"
           class="form-control ${not empty errors.username ? 'is-invalid' : ''}">
    <div class="invalid-feedback"><c:out value="${errors.username}"/></div>
  </div>
  <div class="mb-3">
    <label class="form-label">Mật khẩu</label>
    <input type="password" name="password" required
           class="form-control ${not empty errors.password ? 'is-invalid' : ''}">
    <div class="invalid-feedback"><c:out value="${errors.password}"/></div>
  </div>
  <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
</form>
<div class="d-flex justify-content-between mt-3 small">
  <a href="<c:url value='/forgot-password'/>">Quên mật khẩu?</a>
  <a href="<c:url value='/register'/>">Đăng ký</a>
</div>
</body></html>
