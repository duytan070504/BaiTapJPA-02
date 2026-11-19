<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html><head><title>Thêm Category</title></head>
<body>
<div class="card shadow-sm" style="max-width:600px"><div class="card-body p-4">
  <h4 class="mb-4">Thêm Category</h4>
  <form action="<c:url value='/admin/category/insert'/>" method="post" enctype="multipart/form-data">
    <div class="mb-3">
      <label class="form-label">Category name</label>
      <input type="text" name="categoryname" required maxlength="255" value="<c:out value='${param.categoryname}'/>"
             class="form-control ${not empty errors.categoryname ? 'is-invalid' : ''}">
      <div class="invalid-feedback"><c:out value="${errors.categoryname}"/></div>
    </div>
    <div class="mb-3">
      <label class="form-label">Link images</label>
      <input type="text" name="images" value="<c:out value='${param.images}'/>" placeholder="https://..."
             class="form-control ${not empty errors.images ? 'is-invalid' : ''}">
      <div class="invalid-feedback"><c:out value="${errors.images}"/></div>
    </div>
    <div class="mb-3">
      <label class="form-label">Upload images</label>
      <input type="file" name="images1" accept="image/*" class="form-control ${not empty errors.images1 ? 'is-invalid' : ''}">
      <div class="invalid-feedback"><c:out value="${errors.images1}"/></div>
    </div>
    <div class="mb-3">
      <label class="form-label d-block">Status</label>
      <div class="form-check form-check-inline"><input class="form-check-input" type="radio" name="status" id="s1" value="1" ${param.status != '0' ? 'checked' : ''}><label class="form-check-label" for="s1">Hoạt động</label></div>
      <div class="form-check form-check-inline"><input class="form-check-input" type="radio" name="status" id="s0" value="0" ${param.status == '0' ? 'checked' : ''}><label class="form-check-label" for="s0">Khóa</label></div>
      <c:if test="${not empty errors.status}"><div class="text-danger small"><c:out value="${errors.status}"/></div></c:if>
    </div>
    <button type="submit" class="btn btn-primary">Insert</button>
    <a class="btn btn-outline-secondary" href="<c:url value='/admin/categories'/>">Hủy</a>
  </form>
</div></div>
</body></html>
