<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="_header.jsp" %>
<div class="box narrow" style="max-width:600px">
 <h2>${empty video ? 'Thêm video' : 'Sửa video'}</h2>
 <c:if test="${not empty error}"><div class="err"><c:out value="${error}"/></div></c:if>
 <form action="<c:url value='${empty video ? "/video/insert" : "/video/update"}'/>" method="post" enctype="multipart/form-data">
  <c:if test="${not empty video}"><input type="hidden" name="id" value="${video.id}"></c:if>
  <label>Tiêu đề</label><input type="text" name="title" value="<c:out value='${video.title}'/>" required>
  <label>Mô tả</label><textarea name="description" rows="4"><c:out value="${video.description}"/></textarea>
  <label>Danh mục</label>
  <select name="categoryId">
   <option value="0">-- Không chọn --</option>
   <c:forEach items="${categories}" var="c">
    <option value="${c.categoryId}" ${video.category.categoryId == c.categoryId ? 'selected' : ''}><c:out value="${c.categoryname}"/></option>
   </c:forEach>
  </select>
  <label>Poster (ảnh)</label>
  <c:if test="${not empty video.poster}"><img height="80" src="<c:url value='/image'><c:param name='fname' value='${video.poster}'/></c:url>"><br></c:if>
  <input type="file" name="posterFile" accept="image/*">
  <label>File video (mp4/webm)</label>
  <c:if test="${not empty video.videoFile}"><small>Hiện tại: <c:out value="${video.videoFile}"/> (chọn file mới để thay)</small><br></c:if>
  <input type="file" name="videoFileUpload" accept="video/*">
  <label><input type="checkbox" name="active" ${empty video || video.active ? 'checked' : ''}> Hiển thị (active)</label>
  <button type="submit">${empty video ? 'Thêm' : 'Cập nhật'}</button>
  <a class="btn gray" href="<c:url value='/video'/>">Hủy</a>
 </form>
</div>
<%@ include file="_footer.jsp" %>
