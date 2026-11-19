<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html><head><title>Kích hoạt tài khoản</title></head>
<body>
<h4 class="text-center mb-3">Kích hoạt tài khoản</h4>
<p>Mã OTP đã được gửi tới <b><c:out value="${email}"/></b> (hiệu lực 5 phút).</p>
<c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
<c:if test="${not empty msg}"><div class="alert alert-success"><c:out value="${msg}"/></div></c:if>
<form action="<c:url value='/verify-otp'/>" method="post">
  <input type="hidden" name="email" value="<c:out value='${email}'/>">
  <div class="mb-3">
    <label class="form-label">Mã OTP</label>
    <input type="text" name="otp" required maxlength="6" pattern="[0-9]{6}" title="Gồm đúng 6 chữ số"
           class="form-control ${not empty errors.otp ? 'is-invalid' : ''}">
    <div class="invalid-feedback"><c:out value="${errors.otp}"/></div>
  </div>
  <button type="submit" class="btn btn-primary">Xác nhận</button>
  <a class="btn btn-outline-secondary" href="<c:url value='/resend-otp'><c:param name='email' value='${email}'/></c:url>">Gửi lại OTP</a>
</form>
</body></html>
