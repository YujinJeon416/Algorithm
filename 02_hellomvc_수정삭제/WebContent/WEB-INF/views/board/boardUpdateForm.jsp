<%@page import="board.model.vo.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>    
<%
	Board board = (Board) request.getAttribute("board");
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/board.css" />
<section id="board-container">
<h2>게시판 수정</h2>
<form 
	name="boardUpdateFrm" 
	action="<%=request.getContextPath() %>/board/boardUpdate" 
	enctype="multipart/form-data"
	method="post">
	<input type="hidden" name="no" value="<%= board.getNo() %>" />
	<table id="tbl-board-view">
	<tr>
		<th>제 목</th>
		<td><input type="text" name="title" value="<%= board.getTitle() %>" required></td>
	</tr>
	<tr>
		<th>작성자</th>
		<td>
			<input type="text" name="writer" value="<%= board.getWriter() %>" readonly/>
		</td>
	</tr>
	<tr>
		<th>첨부파일</th>
		<td >
			<%-- input:file의 value속성은 보안성을 이유로 임의변경할 수 없다. --%>
			<input type="file" name="upFile" value="">
			<% if(board.getAttach() != null) { %>
			<p style="margin: 5px 0;">
				<img src="<%= request.getContextPath() %>/images/file.png" width="16px" />
				<%= board.getAttach().getOriginalFileName() %>
				<input type="checkbox" name="delFile" id="delFile" value="<%= board.getAttach().getNo() %>"/>
				<label for="delFile">삭제</label>
			</p>
			<% } %>
		</td>
	</tr>
	<tr>
		<th>내 용</th>
		<td><textarea rows="5" cols="50" name="content"><%= board.getContent() %></textarea></td>
	</tr>
	<tr>
		<th colspan="2">
			<input type="submit" value="수정하기"/>
			<input type="button" value="취소" onclick="history.go(-1);"/>
		</th>
	</tr>
</table>
</form>
</section>
<script>
$("[name=upFile]").change(function(){
	console.log($(this).val());
	if($(this).val() != ""){
		//파일 선택
		$("#delFile").prop("checked", true);
	}
	else {
		//파일 선택 취소
		$("#delFile").prop("checked", false);
	}
});


$(document.boardUpdateFrm).submit(function (){
	var $content = $("[name=content]");
	if(/^(.|\n)+$/.test($content.val()) == false){
		alert("내용을 입력하세요");
		return false;
	}
	return true;
});
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>