<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html><head><title><c:out value="${video.title}"/></title></head>
<body>
<div class="card shadow-sm"><div class="card-body">
  <h3><c:out value="${video.title}"/></h3>
  <c:choose>
    <c:when test="${not empty video.videoFile}">
      <video class="w-100 bg-black rounded" style="max-height:520px" controls
        <c:if test="${not empty video.poster}">poster="<c:url value='/image'><c:param name='fname' value='${video.poster}'/></c:url>"</c:if>>
        <source src="<c:url value='/image'><c:param name='fname' value='${video.videoFile}'/></c:url>">
        Trình duyệt không hỗ trợ video.
      </video>
    </c:when>
    <c:otherwise><div class="alert alert-warning">Video này chưa có file để phát.</div></c:otherwise>
  </c:choose>
  <p class="text-muted small mt-3">
    Đăng bởi <b><c:out value="${video.user.fullname}"/></b> · ${video.views} lượt xem
    <c:if test="${not empty video.category}"> · Danh mục: <c:out value="${video.category.categoryname}"/></c:if>
  </p>
  <p><c:out value="${video.description}"/></p>
  <a class="btn btn-outline-secondary" href="javascript:history.back()">Quay lại</a>
</div></div>
</body></html>
