<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html><head><title>Video của tôi</title></head>
<body>
<div class="d-flex justify-content-between align-items-center mb-3">
  <h3 class="mb-0">Video của tôi</h3>
  <a class="btn btn-primary" href="<c:url value='/video/add'/>">+ Thêm video</a>
</div>
<div class="table-responsive">
<table class="table table-bordered table-hover align-middle bg-white">
  <thead class="table-light"><tr><th>STT</th><th>Poster</th><th>Tiêu đề</th><th>Danh mục</th><th>Lượt xem</th><th>Trạng thái</th><th>Thao tác</th></tr></thead>
  <tbody>
  <c:forEach items="${videos}" var="v" varStatus="st">
    <tr>
      <td>${(page-1)*6 + st.index + 1}</td>
      <td><c:if test="${not empty v.poster}"><img height="60" alt="" src="<c:url value='/image'><c:param name='fname' value='${v.poster}'/></c:url>"></c:if></td>
      <td><a href="<c:url value='/watch'><c:param name='id' value='${v.id}'/></c:url>"><c:out value="${v.title}"/></a></td>
      <td><c:out value="${v.category.categoryname}"/></td>
      <td>${v.views}</td>
      <td>${v.active ? 'Hiển thị' : 'Ẩn'}</td>
      <td>
        <a class="btn btn-sm btn-outline-primary" href="<c:url value='/video/edit'><c:param name='id' value='${v.id}'/></c:url>">Sửa</a>
        <a class="btn btn-sm btn-outline-danger" onclick="return confirm('Xóa video này?')" href="<c:url value='/video/delete'><c:param name='id' value='${v.id}'/></c:url>">Xóa</a>
      </td>
    </tr>
  </c:forEach>
  <c:if test="${empty videos}"><tr><td colspan="7" class="text-center text-muted">Bạn chưa có video nào.</td></tr></c:if>
  </tbody>
</table>
</div>
<nav><ul class="pagination">
  <c:forEach begin="1" end="${totalPages}" var="p">
    <li class="page-item ${p==page ? 'active' : ''}"><a class="page-link" href="<c:url value='/video'><c:param name='page' value='${p}'/></c:url>">${p}</a></li>
  </c:forEach>
</ul></nav>
</body></html>
