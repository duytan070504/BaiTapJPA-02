<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand fw-bold" href="<c:url value='/home'/>">&#9654; VideoShare</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="mainNav">
      <ul class="navbar-nav me-auto">
        <li class="nav-item"><a class="nav-link" href="<c:url value='/home'/>">Trang ch&#7911;</a></li>
        <c:if test="${not empty sessionScope.user}">
          <li class="nav-item"><a class="nav-link" href="<c:url value='/video'/>">Video c&#7911;a t&ocirc;i</a></li>
          <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/categories'/>">Danh m&#7909;c</a></li>
        </c:if>
      </ul>
      <ul class="navbar-nav">
        <c:choose>
          <c:when test="${empty sessionScope.user}">
            <li class="nav-item"><a class="nav-link" href="<c:url value='/login'/>">&#272;&#259;ng nh&#7853;p</a></li>
            <li class="nav-item"><a class="nav-link" href="<c:url value='/register'/>">&#272;&#259;ng k&yacute;</a></li>
          </c:when>
          <c:otherwise>
            <li class="nav-item"><span class="navbar-text me-3">Xin ch&agrave;o, <c:out value="${empty sessionScope.user.fullname ? sessionScope.user.username : sessionScope.user.fullname}"/></span></li>
            <li class="nav-item"><a class="btn btn-outline-light btn-sm" href="<c:url value='/logout'/>">&#272;&#259;ng xu&#7845;t</a></li>
          </c:otherwise>
        </c:choose>
      </ul>
    </div>
  </div>
</nav>
