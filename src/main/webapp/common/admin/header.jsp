<nav class="navbar navbar-dark bg-primary">
  <div class="container-fluid">
    <a class="navbar-brand fw-bold" href="<c:url value='/admin/categories'/>">Qu&#7843;n tr&#7883;</a>
    <div class="d-flex align-items-center text-white">
      <span class="me-3">Xin ch&agrave;o, <c:out value="${empty sessionScope.user.fullname ? sessionScope.user.username : sessionScope.user.fullname}"/></span>
      <a class="btn btn-light btn-sm" href="<c:url value='/logout'/>">&#272;&#259;ng xu&#7845;t</a>
    </div>
  </div>
</nav>
