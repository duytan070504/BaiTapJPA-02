<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="_header.jsp" %>
<div class="box narrow">
 <h2>Thêm Category</h2>
 <c:if test="${not empty error}"><div class="err"><c:out value="${error}"/></div></c:if>
 <form action="<c:url value='/admin/category/insert'/>" method="post" enctype="multipart/form-data">
  <label>Category name</label><input type="text" name="categoryname" required>
  <label>Link images</label><input type="text" name="images">
  <label>Upload images</label><input type="file" name="images1" accept="image/*">
  <label>Status</label>
  <label style="font-weight:normal"><input type="radio" name="status" value="1" checked> Hoạt động</label>
  <label style="font-weight:normal"><input type="radio" name="status" value="0"> Khóa</label>
  <button type="submit">Insert</button>
  <a class="btn gray" href="<c:url value='/admin/categories'/>">Hủy</a>
 </form>
</div>
<%@ include file="_footer.jsp" %>
