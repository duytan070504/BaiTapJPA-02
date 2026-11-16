<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="_header.jsp" %>
<div class="box narrow">
 <h2>Kích hoạt tài khoản</h2>
 <p>Mã OTP đã được gửi tới <b><c:out value="${email}"/></b> (hiệu lực 5 phút).</p>
 <c:if test="${not empty error}"><div class="err"><c:out value="${error}"/></div></c:if>
 <c:if test="${not empty msg}"><div class="ok"><c:out value="${msg}"/></div></c:if>
 <form action="<c:url value='/verify-otp'/>" method="post">
  <input type="hidden" name="email" value="<c:out value='${email}'/>">
  <label>Mã OTP</label><input type="text" name="otp" maxlength="6" required>
  <button type="submit">Xác nhận</button>
  <a class="btn gray" href="<c:url value='/resend-otp'><c:param name='email' value='${email}'/></c:url>">Gửi lại OTP</a>
 </form>
</div>
<%@ include file="_footer.jsp" %>
