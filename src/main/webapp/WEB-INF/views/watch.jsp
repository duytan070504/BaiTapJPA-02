<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="_header.jsp" %>
<div class="box">
 <h2><c:out value="${video.title}"/></h2>
 <c:choose>
  <c:when test="${not empty video.videoFile}">
   <video controls width="100%" style="max-height:520px;background:#000"
     <c:if test="${not empty video.poster}">poster="<c:url value='/image'><c:param name='fname' value='${video.poster}'/></c:url>"</c:if>>
    <source src="<c:url value='/image'><c:param name='fname' value='${video.videoFile}'/></c:url>">
    Trình duyệt không hỗ trợ video.
   </video>
  </c:when>
  <c:otherwise><div class="err">Video này chưa có file để phát.</div></c:otherwise>
 </c:choose>
 <p><small>Đăng bởi <b><c:out value="${video.user.fullname}"/></b> · ${video.views} lượt xem
  <c:if test="${not empty video.category}"> · Danh mục: <c:out value="${video.category.categoryname}"/></c:if></small></p>
 <p><c:out value="${video.description}"/></p>
 <a class="btn gray" href="javascript:history.back()">Quay lại</a>
</div>
<%@ include file="_footer.jsp" %>
