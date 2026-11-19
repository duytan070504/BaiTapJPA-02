<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html><head><title>Danh mục</title></head>
<body>
<h3 class="mb-3">Danh mục (Category)</h3>
<div class="d-flex gap-2 mb-3">
  <a class="btn btn-primary" href="<c:url value='/admin/category/add'/>">+ Add Category</a>
  <form class="d-flex gap-2" action="<c:url value='/admin/categories'/>" method="get">
    <input type="text" name="keyword" class="form-control" placeholder="Tìm theo tên" value="<c:out value='${param.keyword}'/>">
    <button class="btn btn-outline-secondary" type="submit">Tìm</button>
  </form>
</div>
<div class="table-responsive">
<table class="table table-bordered table-hover align-middle bg-white">
  <thead class="table-light"><tr><th>STT</th><th>Images</th><th>Category name</th><th>Status</th><th>Action</th></tr></thead>
  <tbody>
  <c:forEach items="${listcate}" var="cate" varStatus="STT">
    <c:choose>
      <c:when test="${fn:startsWith(cate.images,'http')}"><c:set var="imgUrl" value="${cate.images}"/></c:when>
      <c:otherwise><c:url var="imgUrl" value="/image"><c:param name="fname" value="${cate.images}"/></c:url></c:otherwise>
    </c:choose>
    <tr>
      <td>${STT.index+1}</td>
      <td><img height="80" width="120" style="object-fit:cover" alt="" src="${imgUrl}"></td>
      <td><c:out value="${cate.categoryname}"/></td>
      <td>${cate.status==1 ? 'Hoạt động' : 'Khóa'}</td>
      <td>
        <a class="btn btn-sm btn-outline-primary" href="<c:url value='/admin/category/edit'><c:param name='id' value='${cate.categoryId}'/></c:url>">Sửa</a>
        <a class="btn btn-sm btn-outline-danger" onclick="return confirm('Xóa category này?')" href="<c:url value='/admin/category/delete'><c:param name='id' value='${cate.categoryId}'/></c:url>">Xóa</a>
      </td>
    </tr>
  </c:forEach>
  <c:if test="${empty listcate}"><tr><td colspan="5" class="text-center text-muted">Chưa có danh mục nào.</td></tr></c:if>
  </tbody>
</table>
</div>
</body></html>
