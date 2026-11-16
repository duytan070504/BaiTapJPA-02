<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="_header.jsp" %>
<div class="box">
 <h2>Danh mục (Category)</h2>
 <a class="btn" href="<c:url value='/admin/category/add'/>">+ Add Category</a>
 <form action="<c:url value='/admin/categories'/>" method="get" style="display:inline-block;margin-left:12px">
  <input type="text" name="keyword" placeholder="Tìm theo tên" value="<c:out value='${param.keyword}'/>" style="width:200px">
  <button type="submit">Tìm</button>
 </form>
 <br><br>
 <table>
  <tr><th>STT</th><th>Images</th><th>Category name</th><th>Status</th><th>Action</th></tr>
  <c:forEach items="${listcate}" var="cate" varStatus="STT">
   <c:choose>
    <c:when test="${fn:startsWith(cate.images,'http')}"><c:set var="imgUrl" value="${cate.images}"/></c:when>
    <c:otherwise><c:url var="imgUrl" value="/image"><c:param name="fname" value="${cate.images}"/></c:url></c:otherwise>
   </c:choose>
   <tr>
    <td>${STT.index+1}</td>
    <td><img height="100" width="150" style="object-fit:cover" src="${imgUrl}" alt=""></td>
    <td><c:out value="${cate.categoryname}"/></td>
    <td>${cate.status==1 ? 'Hoạt động' : 'Khóa'}</td>
    <td>
     <a href="<c:url value='/admin/category/edit'><c:param name='id' value='${cate.categoryId}'/></c:url>">Sửa</a> |
     <a href="<c:url value='/admin/category/delete'><c:param name='id' value='${cate.categoryId}'/></c:url>" onclick="return confirm('Xóa category này?')">Xóa</a>
    </td>
   </tr>
  </c:forEach>
 </table>
</div>
<%@ include file="_footer.jsp" %>
