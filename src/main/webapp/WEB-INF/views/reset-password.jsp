<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html><head><title>Đặt lại mật khẩu</title></head>
<body>
<h4 class="text-center mb-3">Đặt lại mật khẩu</h4>
<p>Mã OTP đã gửi tới <b><c:out value="${email}"/></b>.</p>
<c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
<form action="<c:url value='/reset-password'/>" method="post">
  <input type="hidden" name="email" value="<c:out value='${email}'/>">
  <div class="mb-3">
    <label class="form-label">Mã OTP</label>
    <input type="text" name="otp" required maxlength="6" pattern="[0-9]{6}" title="Gồm đúng 6 chữ số"
           class="form-control ${not empty errors.otp ? 'is-invalid' : ''}">
    <div class="invalid-feedback"><c:out value="${errors.otp}"/></div>
  </div>
  <div class="mb-3">
    <label class="form-label">Mật khẩu mới</label>
    <input type="password" name="password" required minlength="6"
           class="form-control ${not empty errors.password ? 'is-invalid' : ''}">
    <div class="invalid-feedback"><c:out value="${errors.password}"/></div>
  </div>
  <div class="mb-3">
    <label class="form-label">Nhập lại mật khẩu mới</label>
    <input type="password" name="confirm" required minlength="6"
           class="form-control ${not empty errors.confirm ? 'is-invalid' : ''}">
    <div class="invalid-feedback"><c:out value="${errors.confirm}"/></div>
  </div>
  <button type="submit" class="btn btn-primary w-100">Đổi mật khẩu</button>
</form>
</body></html>
