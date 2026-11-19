<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html><head><title>Trang chủ</title></head>
<body>
<h3 class="mb-4">10 video mới nhất</h3>
<c:if test="${empty videos}"><div class="alert alert-info">Chưa có video nào.</div></c:if>
<div class="row row-cols-1 row-cols-sm-2 row-cols-lg-4 g-4">
  <c:forEach items="${videos}" var="v">
    <div class="col">
      <div class="card h-100 shadow-sm">
        <a href="<c:url value='/watch'><c:param name='id' value='${v.id}'/></c:url>">
          <c:choose>
            <c:when test="${not empty v.poster}">
              <img class="card-img-top" style="height:150px;object-fit:cover" alt=""
                   src="<c:url value='/image'><c:param name='fname' value='${v.poster}'/></c:url>">
            </c:when>
            <c:otherwise>
              <div class="bg-secondary-subtle d-flex align-items-center justify-content-center text-muted" style="height:150px">No poster</div>
            </c:otherwise>
          </c:choose>
        </a>
        <div class="card-body">
          <h6 class="card-title mb-1">
            <a class="text-decoration-none text-dark stretched-link" href="<c:url value='/watch'><c:param name='id' value='${v.id}'/></c:url>"><c:out value="${v.title}"/></a>
          </h6>
          <small class="text-muted"><c:out value="${v.user.fullname}"/> · ${v.views} lượt xem</small>
        </div>
      </div>
    </div>
  </c:forEach>
</div>
</body></html>
