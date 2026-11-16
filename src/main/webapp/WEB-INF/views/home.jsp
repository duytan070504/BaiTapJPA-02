<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="_header.jsp" %>
<h2>10 video mới nhất</h2>
<c:if test="${empty videos}"><p>Chưa có video nào.</p></c:if>
<div class="grid">
 <c:forEach items="${videos}" var="v">
  <div class="card">
   <a href="<c:url value='/watch'><c:param name='id' value='${v.id}'/></c:url>">
    <c:choose>
     <c:when test="${not empty v.poster}"><img src="<c:url value='/image'><c:param name='fname' value='${v.poster}'/></c:url>" alt=""></c:when>
     <c:otherwise><div class="noimg">No poster</div></c:otherwise>
    </c:choose>
   </a>
   <div class="in">
    <h4><a href="<c:url value='/watch'><c:param name='id' value='${v.id}'/></c:url>"><c:out value="${v.title}"/></a></h4>
    <small><c:out value="${v.user.fullname}"/> · ${v.views} lượt xem</small>
   </div>
  </div>
 </c:forEach>
</div>
<%@ include file="_footer.jsp" %>
