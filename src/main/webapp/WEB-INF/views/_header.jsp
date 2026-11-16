<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>BaiTapJPA-02</title>
<style>
 body{font-family:Arial,sans-serif;margin:0;background:#f4f5f7;color:#222}
 nav{background:#1f2937;padding:12px 24px;display:flex;gap:18px;align-items:center}
 nav a{color:#fff;text-decoration:none} nav .sp{flex:1}
 .wrap{max-width:1000px;margin:24px auto;padding:0 16px}
 .box{background:#fff;padding:24px;border-radius:8px;box-shadow:0 1px 4px rgba(0,0,0,.1)}
 .narrow{max-width:420px;margin:0 auto}
 label{display:block;margin-top:12px;font-weight:bold}
 input[type=text],input[type=password],input[type=email],select,textarea{width:100%;padding:8px;box-sizing:border-box;margin-top:4px}
 button,.btn{background:#2563eb;color:#fff;border:0;padding:9px 16px;border-radius:4px;cursor:pointer;text-decoration:none;display:inline-block;margin-top:16px}
 .btn.red{background:#dc2626}.btn.gray{background:#6b7280}
 .err{background:#fee2e2;color:#991b1b;padding:10px;border-radius:4px;margin-bottom:10px}
 .ok{background:#dcfce7;color:#166534;padding:10px;border-radius:4px;margin-bottom:10px}
 .grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(220px,1fr));gap:16px}
 .card{background:#fff;border-radius:8px;overflow:hidden;box-shadow:0 1px 4px rgba(0,0,0,.1)}
 .card img,.noimg{width:100%;height:130px;object-fit:cover;background:#d1d5db;display:flex;align-items:center;justify-content:center;color:#6b7280}
 .card .in{padding:10px}.card h4{margin:0 0 6px}.card a{color:#111;text-decoration:none}
 table{width:100%;border-collapse:collapse}th,td{border:1px solid #ddd;padding:8px;text-align:left}
 .pager{margin-top:16px;display:flex;gap:6px}.pager a{padding:6px 12px;background:#e5e7eb;color:#111;text-decoration:none;border-radius:4px}
 .pager a.cur{background:#2563eb;color:#fff}
</style>
</head>
<body>
<nav>
 <a href="<c:url value='/home'/>"><b>Trang ch&#7911;</b></a>
 <c:if test="${not empty sessionScope.user}">
  <a href="<c:url value='/video'/>">Video c&#7911;a t&ocirc;i</a>
  <a href="<c:url value='/admin/categories'/>">Danh m&#7909;c</a>
 </c:if>
 <span class="sp"></span>
 <c:choose>
  <c:when test="${empty sessionScope.user}">
   <a href="<c:url value='/login'/>">&#272;&#259;ng nh&#7853;p</a>
   <a href="<c:url value='/register'/>">&#272;&#259;ng k&yacute;</a>
  </c:when>
  <c:otherwise>
   <span style="color:#d1d5db">Xin ch&agrave;o, <c:out value="${empty sessionScope.user.fullname ? sessionScope.user.username : sessionScope.user.fullname}"/></span>
   <a href="<c:url value='/logout'/>">&#272;&#259;ng xu&#7845;t</a>
  </c:otherwise>
 </c:choose>
</nav>
<div class="wrap">