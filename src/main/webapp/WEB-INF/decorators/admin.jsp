<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><sitemesh:write property="title"/> - Admin</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<sitemesh:write property="head"/>
</head>
<body class="bg-light d-flex flex-column min-vh-100">
  <%@ include file="/common/admin/header.jsp" %>
  <div class="container-fluid flex-grow-1">
    <div class="row">
      <aside class="col-md-2 py-4">
        <%@ include file="/common/admin/left.jsp" %>
      </aside>
      <main class="col-md-10 py-4">
        <sitemesh:write property="body"/>
      </main>
    </div>
  </div>
  <%@ include file="/common/admin/footer.jsp" %>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
