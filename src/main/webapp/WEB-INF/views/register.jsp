<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html><head><title>Đăng ký</title></head>
<body>
<h4 class="text-center mb-4">Đăng ký</h4>
<c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
<form action="<c:url value='/register'/>" method="post">
  <div class="mb-3">
    <label class="form-label">Tên đăng nhập</label>
    <input type="text" name="username" required minlength="4" maxlength="30" pattern="[A-Za-z0-9_.]{4,30}"
           title="4-30 ký tự: chữ, số, _ hoặc ." value="<c:out value='${param.username}'/>"
           class="form-control ${not empty errors.username ? 'is-invalid' : ''}">
    <div class="invalid-feedback"><c:out value="${errors.username}"/></div>
  </div>
  <div class="mb-3">
    <label class="form-label">Họ tên</label>
    <input type="text" name="fullname" required maxlength="100" value="<c:out value='${param.fullname}'/>"
           class="form-control ${not empty errors.fullname ? 'is-invalid' : ''}">
    <div class="invalid-feedback"><c:out value="${errors.fullname}"/></div>
  </div>
  <div class="mb-3">
    <label class="form-label">Email (nhận mã OTP)</label>
    <input type="email" name="email" required value="<c:out value='${param.email}'/>"
           class="form-control ${not empty errors.email ? 'is-invalid' : ''}">
    <div class="invalid-feedback"><c:out value="${errors.email}"/></div>
  </div>
  <div class="mb-3">
    <label class="form-label">Số điện thoại</label>
    <input type="text" name="phone" required pattern="(0|\+84)[0-9]{9}" title="10 số, bắt đầu bằng 0 hoặc +84"
           value="<c:out value='${param.phone}'/>"
           class="form-control ${not empty errors.phone ? 'is-invalid' : ''}">
    <div class="invalid-feedback"><c:out value="${errors.phone}"/></div>
  </div>
  <div class="mb-3">
    <label class="form-label">Mật khẩu (tối thiểu 6 ký tự)</label>
    <input type="password" name="password" required minlength="6"
           class="form-control ${not empty errors.password ? 'is-invalid' : ''}">
    <div class="invalid-feedback"><c:out value="${errors.password}"/></div>
  </div>
  <div class="mb-3">
    <label class="form-label">Nhập lại mật khẩu</label>
    <input type="password" name="confirm" required minlength="6"
           class="form-control ${not empty errors.confirm ? 'is-invalid' : ''}">
    <div class="invalid-feedback"><c:out value="${errors.confirm}"/></div>
  </div>
  <button type="submit" class="btn btn-primary w-100">Đăng ký &amp; gửi OTP</button>
</form>
<p class="mt-3 mb-0 small text-center">Đã có tài khoản? <a href="<c:url value='/login'/>">Đăng nhập</a></p>
</body></html>
