<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html><head><title>Quên mật khẩu</title></head>
<body>
<h4 class="text-center mb-4">Quên mật khẩu</h4>
<c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
<form action="<c:url value='/forgot-password'/>" method="post">
  <div class="mb-3">
    <label class="form-label">Email đã đăng ký</label>
    <input type="email" name="email" required value="<c:out value='${param.email}'/>"
           class="form-control ${not empty errors.email ? 'is-invalid' : ''}">
    <div class="invalid-feedback"><c:out value="${errors.email}"/></div>
  </div>
  <button type="submit" class="btn btn-primary w-100">Gửi mã OTP</button>
</form>
<p class="mt-3 mb-0 small text-center"><a href="<c:url value='/login'/>">Quay lại đăng nhập</a></p>
</body></html>
