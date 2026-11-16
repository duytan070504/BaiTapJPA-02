<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="_header.jsp" %>
<div class="box">
 <h2>Video của tôi</h2>
 <a class="btn" href="<c:url value='/video/add'/>">+ Thêm video</a>
 <br><br>
 <table>
  <tr><th>STT</th><th>Poster</th><th>Tiêu đề</th><th>Danh mục</th><th>Lượt xem</th><th>Trạng thái</th><th>Thao tác</th></tr>
  <c:forEach items="${videos}" var="v" varStatus="st">
   <tr>
    <td>${(page-1)*6 + st.index + 1}</td>
    <td>
     <c:if test="${not empty v.poster}"><img height="60" src="<c:url value='/image'><c:param name='fname' value='${v.poster}'/></c:url>"></c:if>
    </td>
    <td><a href="<c:url value='/watch'><c:param name='id' value='${v.id}'/></c:url>"><c:out value="${v.title}"/></a></td>
    <td><c:out value="${v.category.categoryname}"/></td>
    <td>${v.views}</td>
    <td>${v.active ? 'Hiển thị' : 'Ẩn'}</td>
    <td>
     <a href="<c:url value='/video/edit'><c:param name='id' value='${v.id}'/></c:url>">Sửa</a> |
     <a href="<c:url value='/video/delete'><c:param name='id' value='${v.id}'/></c:url>" onclick="return confirm('Xóa video này?')">Xóa</a>
    </td>
   </tr>
  </c:forEach>
  <c:if test="${empty videos}"><tr><td colspan="7">Bạn chưa có video nào.</td></tr></c:if>
 </table>
 <div class="pager">
  <c:forEach begin="1" end="${totalPages}" var="p">
   <a class="${p==page ? 'cur' : ''}" href="<c:url value='/video'><c:param name='page' value='${p}'/></c:url>">${p}</a>
  </c:forEach>
 </div>
</div>
<%@ include file="_footer.jsp" %>
