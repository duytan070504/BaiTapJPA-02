<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html><head><title>${empty video ? 'Thêm video' : 'Sửa video'}</title></head>
<body>
<div class="row justify-content-center"><div class="col-lg-8">
<div class="card shadow-sm"><div class="card-body p-4">
  <h4 class="mb-4">${empty video ? 'Thêm video' : 'Sửa video'}</h4>
  <form action="<c:url value='${empty video ? "/video/insert" : "/video/update"}'/>" method="post" enctype="multipart/form-data">
    <c:if test="${not empty video}"><input type="hidden" name="id" value="${video.id}"></c:if>
    <div class="mb-3">
      <label class="form-label">Tiêu đề</label>
      <input type="text" name="title" required maxlength="255"
             value="<c:out value='${not empty video ? video.title : param.title}'/>"
             class="form-control ${not empty errors.title ? 'is-invalid' : ''}">
      <div class="invalid-feedback"><c:out value="${errors.title}"/></div>
    </div>
    <div class="mb-3">
      <label class="form-label">Mô tả</label>
      <textarea name="description" rows="4" maxlength="4000"
        class="form-control ${not empty errors.description ? 'is-invalid' : ''}"><c:out value="${not empty video ? video.description : param.description}"/></textarea>
      <div class="invalid-feedback"><c:out value="${errors.description}"/></div>
    </div>
    <div class="mb-3">
      <label class="form-label">Danh mục</label>
      <select name="categoryId" class="form-select">
        <option value="0">-- Không chọn --</option>
        <c:forEach items="${categories}" var="c">
          <option value="${c.categoryId}" ${video.category.categoryId == c.categoryId ? 'selected' : ''}><c:out value="${c.categoryname}"/></option>
        </c:forEach>
      </select>
    </div>
    <div class="mb-3">
      <label class="form-label">Poster (ảnh)</label>
      <c:if test="${not empty video.poster}"><div class="mb-2"><img height="80" alt="" src="<c:url value='/image'><c:param name='fname' value='${video.poster}'/></c:url>"></div></c:if>
      <input type="file" name="posterFile" accept="image/*" class="form-control ${not empty errors.posterFile ? 'is-invalid' : ''}">
      <div class="invalid-feedback"><c:out value="${errors.posterFile}"/></div>
    </div>
    <div class="mb-3">
      <label class="form-label">File video (mp4/webm)</label>
      <c:if test="${not empty video.videoFile}"><div class="form-text mb-1">Hiện tại: <c:out value="${video.videoFile}"/> (chọn file mới để thay)</div></c:if>
      <input type="file" name="videoFileUpload" accept="video/*" class="form-control ${not empty errors.videoFileUpload ? 'is-invalid' : ''}">
      <div class="invalid-feedback"><c:out value="${errors.videoFileUpload}"/></div>
    </div>
    <div class="form-check mb-3">
      <input class="form-check-input" type="checkbox" name="active" id="active" ${empty video || video.active ? 'checked' : ''}>
      <label class="form-check-label" for="active">Hiển thị (active)</label>
    </div>
    <button type="submit" class="btn btn-primary">${empty video ? 'Thêm' : 'Cập nhật'}</button>
    <a class="btn btn-outline-secondary" href="<c:url value='/video'/>">Hủy</a>
  </form>
</div></div>
</div></div>
</body></html>
