<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="_header.jsp" %>
<c:choose>
 <c:when test="${fn:startsWith(cate.images,'http')}"><c:set var="imgUrl" value="${cate.images}"/></c:when>
 <c:otherwise><c:url var="imgUrl" value="/image"><c:param name="fname" value="${cate.images}"/></c:url></c:otherwise>
</c:choose>
<div class="box narrow">
 <h2>Sửa Category</h2>
 <form action="<c:url value='/admin/category/update'/>" method="post" enctype="multipart/form-data">
  <input type="hidden" name="categoryId" value="${cate.categoryId}">
  <label>Category name</label><input type="text" name="categoryname" value="<c:out value='${cate.categoryname}'/>" required>
  <label>Link images</label><input type="text" name="images" value="<c:out value='${cate.images}'/>">
  <br><img height="120" src="${imgUrl}" alt="">
  <label>Upload images (chọn để thay ảnh)</label><input type="file" name="images1" accept="image/*">
  <label>Status</label>
  <label style="font-weight:normal"><input type="radio" name="status" value="1" ${cate.status==1 ? 'checked' : ''}> Hoạt động</label>
  <label style="font-weight:normal"><input type="radio" name="status" value="0" ${cate.status!=1 ? 'checked' : ''}> Khóa</label>
  <button type="submit">Update</button>
  <a class="btn gray" href="<c:url value='/admin/categories'/>">Hủy</a>
 </form>
</div>
<%@ include file="_footer.jsp" %>
